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
package org.jacoco.core.diff;

import java.io.Serializable;
import java.util.List;

public class DiffClassInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String packageName;
	private String className;
	private DiffResultTypeEnum diffType;
	private List<DiffMethodInfo> diffMethodInfos;

	/**
	 * 返回Asm解析中对应的类名格式
	 *
	 * @return
	 */
	public String getAsmClassName() {
		// return this.packageName.replaceAll(".","/")+this.className+
		// GitConst.JAVA_FILE_SUFFIX;
		return this.className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public DiffResultTypeEnum getDiffType() {
		return diffType;
	}

	public void setDiffType(DiffResultTypeEnum diffType) {
		this.diffType = diffType;
	}

	public List<DiffMethodInfo> getDiffMethodInfos() {
		return diffMethodInfos;
	}

	public void setDiffMethodInfos(List<DiffMethodInfo> diffMethodInfos) {
		this.diffMethodInfos = diffMethodInfos;
	}

	public static Builder builder(){
		return Builder.aDiffClassInfo();
	}

	public static final class Builder {
		private String packageName;
		private String className;
		private DiffResultTypeEnum diffType;
		private List<DiffMethodInfo> diffMethodInfos;

		private Builder() {
		}

		public static Builder aDiffClassInfo() {
			return new Builder();
		}

		public Builder packageName(String packageName) {
			this.packageName = packageName;
			return this;
		}

		public Builder className(String className) {
			this.className = className;
			return this;
		}

		public Builder diffType(DiffResultTypeEnum diffType) {
			this.diffType = diffType;
			return this;
		}

		public Builder diffMethodInfos(List<DiffMethodInfo> diffMethodInfos) {
			this.diffMethodInfos = diffMethodInfos;
			return this;
		}

		public DiffClassInfo build() {
			DiffClassInfo diffClassInfo = new DiffClassInfo();
			diffClassInfo.setPackageName(packageName);
			diffClassInfo.setClassName(className);
			diffClassInfo.setDiffType(diffType);
			diffClassInfo.setDiffMethodInfos(diffMethodInfos);
			return diffClassInfo;
		}
	}
}
