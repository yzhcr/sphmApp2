package com.nju.sphm.Controller.LoginActivities;

/**
 * Created by hcr1 on 2015/1/7.
 */
public class TreeNode {

    private String contentText;

    private int level;

    private String id;

    private String parendId;

    private boolean hasChildren;

    private boolean isExpanded;

    private String path;


    public static final String NO_PARENT = "";

    public static final int TOP_LEVEL = 0;

    public TreeNode(String contentText, int level, String id, String parendId,
                    boolean hasChildren, boolean isExpanded, String path) {
        super();
        this.contentText = contentText;
        this.level = level;
        this.id = id;
        this.parendId = parendId;
        this.hasChildren = hasChildren;
        this.isExpanded = isExpanded;
        this.path=path;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParendId() {
        return parendId;
    }

    public void setParendId(String parendId) {
        this.parendId = parendId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }


}
