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

public class DiffMethodInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String methodName;
	private String md5;
	private DiffResultTypeEnum diffType;
	private String params;

	public String getMethodUri() {
		return methodName + "#" + params;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public DiffResultTypeEnum getDiffType() {
		return diffType;
	}

	public void setDiffType(DiffResultTypeEnum diffType) {
		this.diffType = diffType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public static Builder builder(){
		return Builder.aDiffMethodInfo();
	}
	public static final class Builder {
		private String methodName;
		private String md5;
		private DiffResultTypeEnum diffType;
		private String params;

		private Builder() {
		}

		public static Builder aDiffMethodInfo() {
			return new Builder();
		}

		public Builder methodName(String methodName) {
			this.methodName = methodName;
			return this;
		}

		public Builder md5(String md5) {
			this.md5 = md5;
			return this;
		}

		public Builder diffType(DiffResultTypeEnum diffType) {
			this.diffType = diffType;
			return this;
		}

		public Builder params(String params) {
			this.params = params;
			return this;
		}

		public DiffMethodInfo build() {
			DiffMethodInfo diffMethodInfo = new DiffMethodInfo();
			diffMethodInfo.setMethodName(methodName);
			diffMethodInfo.setMd5(md5);
			diffMethodInfo.setDiffType(diffType);
			diffMethodInfo.setParams(params);
			return diffMethodInfo;
		}
	}
}
