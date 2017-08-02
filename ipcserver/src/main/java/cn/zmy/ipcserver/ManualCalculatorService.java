package cn.zmy.ipcserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by zmy on 2017/8/2 0002.
 */

public class ManualCalculatorService extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return new Binder()
        {
            @Override
            protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException
            {
                switch (code)
                {
                    case 1000:
                    {
                        int num1 = data.readInt();
                        int num2 = data.readInt();
                        reply.writeInt(num1 + num2);
                        return true;
                    }
                }
                return super.onTransact(code, data, reply, flags);
            }
        };
    }
}
