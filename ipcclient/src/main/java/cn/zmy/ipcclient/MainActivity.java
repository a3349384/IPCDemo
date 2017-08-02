package cn.zmy.ipcclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;

import cn.zmy.ipcserver.ICalculateInterface;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonAIDLConnect).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("cn.zmy.ipcserver", "cn.zmy.ipcserver.CalculatorService"));
                bindService(intent, new ServiceConnection()
                {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service)
                    {
                        try
                        {
                            int result = ICalculateInterface.Stub.asInterface(service).add(1,2);
                            Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                        }
                        catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name)
                    {

                    }
                }, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.buttonManualConnect).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("cn.zmy.ipcserver", "cn.zmy.ipcserver.ManualCalculatorService"));
                bindService(intent, new ServiceConnection()
                {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service)
                    {
                        try
                        {
                            Field field = service.getClass().getDeclaredField("mObject");
                            field.setAccessible(true);
                            field.get(service);
                        }
                        catch (Exception e) {}

                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();

                        data.writeInt(1);
                        data.writeInt(2);
                        try
                        {
                            service.transact(1000, data, reply, 0);
                        }
                        catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }

                        int result = reply.readInt();
                        data.recycle();
                        reply.recycle();
                        Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name)
                    {

                    }
                }, Context.BIND_AUTO_CREATE);
            }
        });
    }
}
