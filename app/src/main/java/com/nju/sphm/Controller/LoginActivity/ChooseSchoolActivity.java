package com.nju.sphm.Controller.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nju.sphm.Model.FinishTheApp.SaveMainActivity;
import com.nju.sphm.Model.Login.GetOrganization;
import com.nju.sphm.R;

import java.util.ArrayList;

public class ChooseSchoolActivity extends Activity {

    ArrayList<TreeNode> topNodes;
    ArrayList<TreeNode> allNodes;
    private Thread thread;
    LayoutInflater inflater;
    @ViewInject(R.id.schoollist)
    ListView treeview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);
        ViewUtils.inject(this);
        //StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }

    private void init() {
        thread=new Thread(runnable);
        thread.start();
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

    private Handler mHandler = new Handler() {
        // 重写handleMessage()方法，此方法在UI线程运行
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 如果成功，则显示从网络获取到的图片
                case 1: {
                    topNodes.add(allNodes.get(0));
                    //ListView treeview = (ListView) findViewById(R.id.schoollist);
                    TreeViewAdapter treeViewAdapter = new TreeViewAdapter(topNodes, allNodes, inflater);
                    TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(treeViewAdapter);
                    treeview.setAdapter(treeViewAdapter);
                    treeview.setOnItemClickListener(treeViewItemClickListener);
                    break;
                }
                // 否则提示失败
                case 0:
                    Toast.makeText(getApplication(),
                            "网络出错，请检查网络环境",
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    Runnable runnable = new Runnable() {
        // 重写run()方法，此方法在新的线程中运行
        @Override
        public void run() {
            try{
                GetOrganization getOrganization=new GetOrganization();
                topNodes = new ArrayList<TreeNode>();
                allNodes = getOrganization.setTreeBean();
                if(allNodes!=null){
                    mHandler.obtainMessage(1).sendToTarget();
                }else{
                    mHandler.obtainMessage(0).sendToTarget();
                }
            } catch (Exception e) {
                mHandler.obtainMessage(0).sendToTarget();
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.finish();
        return super.onKeyDown(keyCode, event);
    }

}
