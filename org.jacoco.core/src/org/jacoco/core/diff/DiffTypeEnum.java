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

public enum DiffTypeEnum {

	BRANCH_DIFF(0, "分支diff"), COMMIT_DIFF(1, "commitId diff"), UNKNOWN(-1,
			"不存在的比对类型");

	private int code;
	private String desc;

	DiffTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static DiffTypeEnum getDiffTypeByCode(int code) {
		for (DiffTypeEnum diffTypeEnum : DiffTypeEnum.values()) {
			if (code == diffTypeEnum.getCode()) {
				return diffTypeEnum;
			}
		}
		return DiffTypeEnum.UNKNOWN;
	}
}
