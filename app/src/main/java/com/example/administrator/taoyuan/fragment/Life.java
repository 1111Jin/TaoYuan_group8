package com.example.administrator.taoyuan.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.taoyuan.R;
//import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class Life extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_life,null);
        return view;
    }

}







//}

//    private ListView lv_lifeinfo;
//    BaseAdapter adapter;
////    final ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//       View view=inflater.inflate(R.layout.activity_activity_life,null);
//

//        lv_lifeinfo = ((ListView) view.findViewById(R.id.lv_dongtai1));
//
//        ImageView view3=new ImageView(getActivity());
//        view3.setAdjustViewBounds(true);//去掉上下空白
//        view3.setImageResource( R.drawable.background);
//        lv_lifeinfo.addHeaderView(view3,null,false);
//
//        adapter=new BaseAdapter() {
//
//
//            @Override
//            public int getCount() {
//                return lifelist.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//
//                View view1=  View.inflate(getActivity(), R.layout.activity_list_lv_dongtai_item,null);
//                ImageView iv_photo = ((ImageView) view1.findViewById(R.id.iv_photo));
//                TextView  tv_title= ((TextView) view1.findViewById(R.id.tv_title));
//                TextView tv_name= ((TextView) view1.findViewById(R.id.tv_name));
//
//
//                ListLifeInfo.LifeInfo dongtai=lifelist.get(position);
//                try {
//                    tv_title.setText(URLDecoder.decode(dongtai.userName,"utf-8"));
//                    tv_name.setText(dongtai.content);
//                    //
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//               // x.image().bind(iv_photo,"http://10.40.5.12:8080/Life/imags/"+dongtai.headphoto+"");
//                xUtilsImageUtils.display(iv_photo,"http://10.40.5.45:8080/Life/imags/"+dongtai.headphoto+"",true);
//
//                return view1;
//            }
//        };
//        lv_lifeinfo.setAdapter(adapter);
//        getLifeInfoList();
//        return view;
//
//
//    }
//
//    private void getLifeInfoList() {
//        RequestParams params = new RequestParams("http://10.40.5.45:8080/Life/getdongraibypage");
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                System.out.println(result);
//                Gson gson=new Gson();
//                ListLifeInfo bean= gson.fromJson(result, ListLifeInfo.class);
////                 System.out.println(bean.status+"----");
////                System.out.println(bean.lifeinfolist.size()+"===");
//                lifelist.addAll( bean.lifeinfolist);
//
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(Life.this.getActivity(),ex.toString(),Toast.LENGTH_LONG).show();
//                System.out.println(ex.toString());
//            }
//
//            @Override
//            public void onCancelled(Callback.CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });

//    }
//    }








