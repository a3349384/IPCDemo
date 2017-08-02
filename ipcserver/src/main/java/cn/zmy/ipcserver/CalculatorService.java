package cn.zmy.ipcserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by zmy on 2017/8/1 0001.
 */

public class CalculatorService extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return new CalculatorBinder();
    }

    static class CalculatorBinder extends ICalculateInterface.Stub
    {
        @Override
        public int add(int num1, int num2) throws RemoteException
        {
            return num1 + num2;
        }
    }
}
