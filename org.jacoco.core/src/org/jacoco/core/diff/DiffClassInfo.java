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

    /**
     * maven模块名字
     */
    private String moduleName;
    /**
     * 全限定类名，不包含.java
     */
    private String absoluteClassName;
    private DiffResultTypeEnum diffType;
    /**
     * diffType==MODIFY，此字段才赋值。注意多次git提交记录下，同个方法会出现多个方法的记录
     */
    private List<DiffMethodInfo> diffMethodInfos;

    /**
     * 唯一标识符
     * @return
     */
    public String getUrl() {
        if (null == moduleName || "".equals(moduleName)) {
            return absoluteClassName;
        }
        return moduleName + "/" + absoluteClassName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getAbsoluteClassName() {
        return this.absoluteClassName;
    }

    public void setAbsoluteClassName(String absoluteClassName) {
        this.absoluteClassName = absoluteClassName;
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

    public static Builder builder() {
        return Builder.aDiffClassInfo();
    }

    public static final class Builder {
        private String moduleName;
        private String absoluteClassName;
        private DiffResultTypeEnum diffType;
        private List<DiffMethodInfo> diffMethodInfos;

        private Builder() {
        }

        public static Builder aDiffClassInfo() {
            return new Builder();
        }

        public Builder moduleName(String moduleName) {
            this.moduleName = moduleName;
            return this;
        }

        public Builder absoluteClassName(String className) {
            this.absoluteClassName = className;
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
            diffClassInfo.setModuleName(moduleName);
            diffClassInfo.setAbsoluteClassName(absoluteClassName);
            diffClassInfo.setDiffType(diffType);
            diffClassInfo.setDiffMethodInfos(diffMethodInfos);
            return diffClassInfo;
        }
    }
}
