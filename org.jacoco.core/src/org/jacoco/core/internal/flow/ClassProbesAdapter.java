/*******************************************************************************
 * Copyright (c) 2009, 2022 Mountainminds GmbH & Co. KG and Contributors
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *
 *******************************************************************************/
package org.jacoco.core.internal.flow;

import org.jacoco.core.data.MethodProbesInfo;
import org.jacoco.core.diff.DiffClassInfo;
import org.jacoco.core.diff.DiffMethodInfo;
import org.jacoco.core.diff.DiffResultTypeEnum;
import org.jacoco.core.diff.MethodUriAdapter;
import org.jacoco.core.internal.analysis.ClassCoverageImpl;
import org.jacoco.core.internal.instr.InstrSupport;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AnalyzerAdapter;

/**
 * A {@link org.objectweb.asm.ClassVisitor} that calculates probes for every
 * method.
 */
public class ClassProbesAdapter extends ClassVisitor
		implements IProbeIdGenerator {

	private static final MethodProbesVisitor EMPTY_METHOD_PROBES_VISITOR = new MethodProbesVisitor() {
	};

	private final ClassProbesVisitor cv;

	private final boolean trackFrames;

	private int counter = 0;

	private String name;

	private final ClassCoverageImpl coverage;
	private final DiffClassInfo diffClassInfo;

	/**
	 * Creates a new adapter that delegates to the given visitor.
	 *
	 * @param cv
	 *            instance to delegate to
	 * @param trackFrames
	 *            if <code>true</code> stackmap frames are tracked and provided
	 */
	public ClassProbesAdapter(final ClassProbesVisitor cv,
			final boolean trackFrames) {
		super(InstrSupport.ASM_API_VERSION, cv);
		this.cv = cv;
		this.trackFrames = trackFrames;

		this.coverage = null;
		this.diffClassInfo = null;
	}

	public ClassProbesAdapter(final ClassProbesVisitor cv,
			final boolean trackFrames, ClassCoverageImpl coverage,
			DiffClassInfo diffClassInfo) {
		super(InstrSupport.ASM_API_VERSION, cv);
		this.cv = cv;
		this.trackFrames = trackFrames;
		this.coverage = coverage;
		this.diffClassInfo = diffClassInfo;
	}

	@Override
	public void visit(final int version, final int access, final String name,
			final String signature, final String superName,
			final String[] interfaces) {
		this.name = name;
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public final MethodVisitor visitMethod(final int access, final String name,
			final String desc, final String signature,
			final String[] exceptions) {
		final MethodProbesVisitor methodProbes;
		final MethodProbesVisitor mv = cv.visitMethod(access, name, desc,
				signature, exceptions);
		if (mv == null) {
			// We need to visit the method in any case, otherwise probe ids
			// are not reproducible
			methodProbes = EMPTY_METHOD_PROBES_VISITOR;
		} else {
			if (this.coverage != null) {
				MethodProbesInfo info = new MethodProbesInfo();
				info.setMethodName(name);
				info.setStartIndex(counter);
				info.setMethodUri(this.name + "." + name + desc);
				info.setDesc(desc);
				this.coverage.getMethodProbesInfos().add(info);
			}
			// 增量
			if (null != diffClassInfo) {
				if (isMatchMethod(access, name, desc, signature, exceptions)) {
					methodProbes = mv;
				} else {
					methodProbes = EMPTY_METHOD_PROBES_VISITOR;
				}
			} else {// 全量
				methodProbes = mv;
			}

			// System.err.println("className: " + this.name + " methodName: "
			// + name + " start count: " + counter);
		}
		return new MethodSanitizer(null, access, name, desc, signature,
				exceptions) {

			@Override
			public void visitEnd() {
				super.visitEnd();
				LabelFlowAnalyzer.markLabels(this);
				final MethodProbesAdapter probesAdapter = new MethodProbesAdapter(
						methodProbes, ClassProbesAdapter.this, coverage);
				if (trackFrames) {
					final AnalyzerAdapter analyzer = new AnalyzerAdapter(
							ClassProbesAdapter.this.name, access, name, desc,
							probesAdapter);
					probesAdapter.setAnalyzer(analyzer);
					methodProbes.accept(this, analyzer);
				} else {
					methodProbes.accept(this, probesAdapter);
				}

				// System.err.println(">>>methodName:" + name + ",desc:" + desc
				// + ",signature:" + signature + ",endCount:"
				// + ClassProbesAdapter.this.getCurrentId());
			}
		};
	}

	/**
	 * 是否方法匹配
	 *
	 * @param access
	 *            the method's access flags (see {@link Opcodes}). This
	 *            parameter also indicates if the method is synthetic and/or
	 *            deprecated.
	 * @param name
	 *            the method's name.
	 * @param desc
	 *            the method's descriptor (see {@link Type}).
	 * @param signature
	 *            the method's signature. May be {@literal null} if the method
	 *            parameters, return type and exceptions do not use generic
	 *            types.
	 * @param exceptions
	 *            the internal names of the method's exception classes (see
	 *            {@link Type#getInternalName()}). May be {@literal null}.
	 * @return
	 */
	private boolean isMatchMethod(final int access, final String name,
			final String desc, final String signature,
			final String[] exceptions) {
		// 如果是新增类,直接返回true
		if (diffClassInfo.getDiffType() == DiffResultTypeEnum.ADD) {
			return true;
		}
		for (DiffMethodInfo diffMethodInfo : diffClassInfo
				.getDiffMethodInfos()) {
			// 过滤掉删除的方法
			if (DiffResultTypeEnum.DEL == diffMethodInfo.getDiffType()) {
				continue;
			}
			// 过滤掉不是同一方法名
			if (!diffMethodInfo.getMethodName().equalsIgnoreCase(name)) {
				continue;
			}
			// 检查参数是否一致
			if (!MethodUriAdapter.checkParamsIn(diffMethodInfo.getParams(),
					desc)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public void visitEnd() {
		cv.visitTotalProbeCount(counter);
		super.visitEnd();
	}

	// === IProbeIdGenerator ===

	public int nextId() {
		return counter++;
	}

	public int getCurrentId() {
		return counter - 1 > -1 ? counter - 1 : -1;
	}

}
