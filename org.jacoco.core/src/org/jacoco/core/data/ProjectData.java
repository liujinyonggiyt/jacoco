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
package org.jacoco.core.data;

/**
 * @author liujinyong
 */
public class ProjectData {
	/**
	 * 当前git commit hash
	 */
	private final String commitId;

	public ProjectData(String commitId) {
		this.commitId = commitId;
	}

	public String getCommitId() {
		return commitId;
	}
}
