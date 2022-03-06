package com.mytest.myappdemo;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import java.text.*;
import android.text.TextUtils;
import java.io.*;
import java.net.Socket;

public class MainActivity extends Activity implements View.OnClickListener
{

  


	//主界面控件
	ImageButton sendButton;
    ImageButton moreButton;
	EditText editmsgtext;
	//主显示视图
	private LinkedList<DATA> mDATA = null;
	private Context mContext;
	private MyAdapter mAdapter=null;
	private ListView ctrlView;

    private int i;
	
    
    //地图活动
    Intent mapactionIntent=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//主界面
		
		//由此开始
		//初始化
		//主界面控件
        i=0;
        
       
        sendButton=findViewById(R.id.sendButton);
        editmsgtext= findViewById( R.id.editMsgText);
        
		
		//主显示视图
		mContext=MainActivity.this;
		ctrlView = findViewById(R.id.mainListView);
		mDATA=new LinkedList<DATA>();
		mAdapter=new MyAdapter(mDATA,mContext);
		ctrlView.setAdapter(mAdapter);
        ctrlView.setTranscriptMode(ctrlView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        ctrlView.setStackFromBottom(true);
      
       //设置控件监听
       
		sendButton.setOnClickListener(this);
		
		//oncreate end
	}
    
    
    //点击事件
    @Override
    public void onClick(View pick) {
        switch( pick.getId() ){

            case R .id .sendButton:
                {
                    i++;
                    if(! TextUtils.isEmpty( editmsgtext.getText() )){


                        mDATA.add(new DATA(
                                      "message "+i+" "+editmsgtext.getText().toString()+"\n",
                                      "127.0.0.1"
                                  ));        //添加数据到界面
                        if(ctrlView.getCount()>10){mDATA.removeFirst();}
                        mAdapter.notifyDataSetChanged();//刷新界面
                        editmsgtext.setText(null);//清空编辑框
                    }
                }
                break;
            case R .id .connect:
                {
                    //连接网络
                
                }
                break;
        }
    }
    //单独设置more按钮的点击事件
    public void onclickmore(View v){
        Toast.makeText(getApplication(), "more button is ok", Toast.LENGTH_SHORT).show();
    }
	//主界面控件

//actionbar菜单

	//右上角菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
		
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
		
		//return super.onCreateOptionsMenu(menu);
	}

	//菜单消息检查
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		switch(item.getItemId())
		{
			case R.id.exit:
				finish();
				return true;
			case R.id.connect:
            {
                //连接网络
                showconnectdialog();
                return true;
            }
            case R.id.mapaction:
                //启动地图活动
                if(mapactionIntent==null){
                mapactionIntent=new Intent(mContext,MapAction.class);
                }
                startActivity(mapactionIntent);
                return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
//主显示视图

	//数据结构
	class DATA
	{
		private String messsage;
		private String time;
		private String ipofcomer;

		public String getmessage()
		{
			return this.messsage;
		}
		public String gettime()
		{
			return this.time;
		}
		public String getipofcomer()
		{
			return this.ipofcomer;
		}
		public DATA(String messsage, String ipofcomer)
		{
			this.messsage = messsage;
			this.time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
			this.ipofcomer = ipofcomer;
		}
		public void setmessage(String messsage)
		{

			this.messsage = messsage;
		}
		public void settime(String time)
		{
			this.time = time;
		}
		public void setipofcomer(String ipofcomer)
		{

			this.ipofcomer = ipofcomer;
		}
	}

	//数据适配器

	class MyAdapter extends BaseAdapter
	{
		private LinkedList<DATA> mDATA;
		private Context mContext;

		MyAdapter(LinkedList<DATA> mDATA, Context mContext)
		{
			this.mDATA = mDATA;
			this.mContext = mContext;
		}

		@Override
		public int getCount()
		{
			// TODO: Implement this method
			return mDATA.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO: Implement this method
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO: Implement this method
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{

			convertView = LayoutInflater.from(mContext).inflate(R.layout.entry, parent, false);

			TextView msgview = convertView.findViewById(R.id.entryTextView_message);
			TextView timeview= convertView.findViewById(R.id.entryTextView_time);
			TextView ipview =  convertView.findViewById(R.id.entryTextView_ipadress);

			msgview.setText(mDATA.get(position).getmessage());
			timeview.setText(mDATA.get(position).gettime());
			ipview.setText(mDATA.get(position).getipofcomer());

			return convertView;
		}

	}
	
//ip对话框
    private void showconnectdialog(){
        AlertDialog.Builder CD = new AlertDialog.Builder( mContext);
        View CDview=LayoutInflater.from(mContext).inflate( R .layout.linkdialog,null);
        CD.setTitle("connect");
        CD.setView(CDview);
        CD.setPositiveButton("link", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2) {
                    Toast.makeText(getApplication(),"正在连接", Toast.LENGTH_SHORT).show();
                }
            });
        CD.setNegativeButton("cancel", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2) {
                    Toast.makeText(getApplication(), "已取消", Toast.LENGTH_SHORT).show();
                }
            });
        CD.show();
    }
    
//Activity end
	
}
