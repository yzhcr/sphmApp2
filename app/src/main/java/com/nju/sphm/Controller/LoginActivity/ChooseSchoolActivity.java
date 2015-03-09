package com.nju.sphm.Controller.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nju.sphm.Model.FinishTheApp.SaveMainActivity;
import com.nju.sphm.Model.Login.GetOrganization;
import com.nju.sphm.R;

import java.util.ArrayList;

public class ChooseSchoolActivity extends Activity {

    ArrayList<TreeNode> topNodes;
    ArrayList<TreeNode> allNodes;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_school);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
        ListView treeview = (ListView) findViewById(R.id.schoollist);
        TreeViewAdapter treeViewAdapter = new TreeViewAdapter(topNodes, allNodes, inflater);
        TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(treeViewAdapter);
        treeview.setAdapter(treeViewAdapter);
        treeview.setOnItemClickListener(treeViewItemClickListener);
    }

    private void init() {
        GetOrganization getOrganization=new GetOrganization();
        topNodes = new ArrayList<TreeNode>();
        allNodes = getOrganization.setTreeBean();

        topNodes.add(allNodes.get(0));
    }
    public class TreeViewItemClickListener implements AdapterView.OnItemClickListener {

        private TreeViewAdapter treeViewAdapter;

        public TreeViewItemClickListener(TreeViewAdapter treeViewAdapter) {
            this.treeViewAdapter = treeViewAdapter;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            TreeNode treeNode = (TreeNode) treeViewAdapter.getItem(position);

            ArrayList<TreeNode> topNodes = treeViewAdapter.getTopNodes();

            ArrayList<TreeNode> allNodes = treeViewAdapter.getAllNodes();


            if (!treeNode.isHasChildren()) {
                SaveMainActivity.getInstance().getMainActivity().finish();
                Intent intent = new Intent();
                intent.putExtra("schoolname",treeNode.getContentText());
                intent.putExtra("schoolid",treeNode.getId());
                intent.putExtra("schoolpath",treeNode.getPath());
                intent.setClass(ChooseSchoolActivity.this, MainActivity.class);
                startActivity(intent);
                ChooseSchoolActivity.this.finish();
            }

            if (treeNode.isExpanded()) {
                treeNode.setExpanded(false);

                ArrayList<TreeNode> elementsToDel = new ArrayList<TreeNode>();
                for (int i = position + 1; i < topNodes.size(); i++) {
                    if (treeNode.getLevel() >= topNodes.get(i).getLevel())
                        break;
                    elementsToDel.add(topNodes.get(i));
                }
                topNodes.removeAll(elementsToDel);
                treeViewAdapter.notifyDataSetChanged();
            } else {
                treeNode.setExpanded(true);

                int i = 1;
                for (TreeNode e : allNodes) {
                    if (e.getParendId().equals(treeNode.getId())) {
                        e.setExpanded(false);
                        topNodes.add(position + i, e);
                        i ++;
                    }
                }
                treeViewAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.finish();
        return super.onKeyDown(keyCode, event);
    }

}
