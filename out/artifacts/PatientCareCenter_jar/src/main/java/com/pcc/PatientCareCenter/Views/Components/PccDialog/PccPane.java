package com.pcc.PatientCareCenter.Views.Components.PccDialog;

public class PccPane<NodeType> {
    protected String header;
    protected String footer;
    protected NodeType pane;

    public PccPane(String header, String footer, NodeType pane) {
        this.header = header;
        this.footer = footer;
        this.pane = pane;
    }

    public PccPane(NodeType pane, String header) {
        this(header, null, pane);
    }

    public PccPane(NodeType pane) {
        this(null, null, pane);
    }
}
