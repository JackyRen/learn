package com.example.learn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
public class LearnActivity extends Activity {
	private TextView city;
	private TextView weather;
	private TextView temp;
	private Button but1;
	Button record;
	Button share;
	Bitmap bmImg;    
	ImageView imView;
	ImageView back;
	Gallery photo;
	
	private static final String APP_ID = "wxadc24f390a71a665";
	private IWXAPI api;
	private void regToWx()
	{
		api = WXAPIFactory.createWXAPI(this, APP_ID, true);
		api.registerApp(APP_ID);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
		regToWx();
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn);
        city = (TextView)this.findViewById(R.id.city);
        weather = (TextView)findViewById(R.id.weather);
        imView = (ImageView) findViewById(R.id.imageView1);    
        temp = (TextView)findViewById(R.id.temp);
        photo = (Gallery)findViewById(R.id.gallery1);
        but1 = (Button)findViewById(R.id.button1);
        but1.setOnClickListener(new Button.OnClickListener()
        {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LearnActivity.this, SettingActivity.class);
				startActivity(intent);
				LearnActivity.this.finish();
			}
        	
        });
        record = (Button)findViewById(R.id.record);
        record.setOnClickListener(new Button.OnClickListener()
        {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LearnActivity.this, RecordActivity.class);
				startActivity(intent);
				LearnActivity.this.finish();
			}
        	
        });
        
        share = (Button)findViewById(R.id.share);
        share.setOnClickListener(new Button.OnClickListener()
        {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String text = city.getText().toString() + "\n" + weather.getText().toString() + "\n" + temp.getText().toString()+"C\n";  
		        WXTextObject textObj = new WXTextObject();  
		        textObj.text = text;  
		        WXMediaMessage msg = new WXMediaMessage(textObj);  
		        msg.mediaObject = textObj;  
		        msg.description = text;  

		        SendMessageToWX.Req req = new SendMessageToWX.Req();  
		        req.transaction = String.valueOf(System.currentTimeMillis());
		        req.scene = SendMessageToWX.Req.WXSceneTimeline;
		        req.message = msg;  
		             
		        api.sendReq(req);
			}
        	
        });
          

        
        
		//imView.setImageBitmap(returnBitMap(imageUrl));
		String read = this.get("http://xml.weather.yahoo.com/forecastrss?w=2151330&u=c");
		String re = "city=\"[A-Za-z]+\"";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(read);
		while(m.find())
		{
			String sub = read.substring(m.start(), m.end());
			city.setText(sub.substring(6,sub.length()-1));
			break;
		}
		re = "text=\"([A-Za-z]| )+\"";
		p = Pattern.compile(re);
		m = p.matcher(read);
		while(m.find())
		{
			String sub = read.substring(m.start(), m.end());
			weather.setText(sub.substring(6, sub.length()-1));
			break;
		}
		re = "code=\"[0-9]+\"";
		p = Pattern.compile(re);
		m = p.matcher(read);
		while(m.find())
		{
			String sub = read.substring(m.start(), m.end());
			String imageUrl = "http://l.yimg.com/a/i/us/we/52/"+sub.substring(6, sub.length()-1)+".gif";
			imView.setImageBitmap(returnBitMap(imageUrl));
			break;
		}
		re = "temp=\"[0-9]+\"";
		p = Pattern.compile(re);
		m = p.matcher(read);
		while(m.find())
		{
			String sub = read.substring(m.start(), m.end());
			temp.setText("Temperature : "+sub.substring(6, sub.length()-1)+"C");
			break;
		}
	
    }
    public Bitmap returnBitMap(String url) {    
		URL myFileUrl = null;    
		Bitmap bitmap = null;    
		try {    
			myFileUrl = new URL(url);    
		} catch (MalformedURLException e) {    
			e.printStackTrace();    
		}    
		try {    
			HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();    
			conn.setDoInput(true);    
			conn.connect();    
			InputStream is = conn.getInputStream();    
			bitmap = BitmapFactory.decodeStream(is);    
			is.close();    
		} catch (IOException e) {    
			e.printStackTrace();    
		}    
		return bitmap;    
	}
	
	public static String get(String uri) {
		BufferedReader reader = null;
		StringBuffer sb = null;
		String result = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(uri);
		try {
			// 发送请求，得到响应
			HttpResponse response = client.execute(request);

			// 请求成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				sb = new StringBuffer();
				String line = "";
				// String NL = System.getProperty("line.separator");
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}
		} catch (ClientProtocolException e) {
			//android.util.Log.d("", "protocol");
			e.printStackTrace();
		} catch (IOException e) {
			//android.util.Log.d("", "io1");
			e.printStackTrace();
		} catch (Exception e) {
			//android.util.Log.d("", "io1");
			e.printStackTrace();
		} 
		finally {
			try {
				if (null != reader) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				//android.util.Log.d("", "io2");
				e.printStackTrace();
			}
		}
		if (null != sb) {
			result = sb.toString();
		}
		return result;
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.learn, menu);
        return true;
    }
}
