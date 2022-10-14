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

public enum DiffResultTypeEnum implements Serializable {

	DEL(0, "delete"), ADD(1, "add"), MODIFY(2, "modify");

	private Integer code;
	private String desc;

	DiffResultTypeEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
