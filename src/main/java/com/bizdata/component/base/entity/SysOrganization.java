package com.bizdata.component.base.entity;

public class SysOrganization {
    private Long id;

    private String name;

    private Long parent;

    private Boolean expanded=true;

    private Boolean loaded=true;

    private String level;

    private Boolean isleaf=true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Boolean getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Boolean isleaf) {
        this.isleaf = isleaf;
    }
}