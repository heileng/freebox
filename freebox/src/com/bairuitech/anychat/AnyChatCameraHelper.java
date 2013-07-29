package com.bairuitech.anychat;


import java.util.List;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;


// AnyChat Camera��װ�࣬ʵ�ֱ�����Ƶ�ɼ�
public class AnyChatCameraHelper implements SurfaceHolder.Callback{
	private final static String TAG = "ANYCHAT";
	private Camera mCamera =null;
	private boolean bIfPreview = false;
	private boolean bNeedCapture = false;
	private int iCurrentCameraId = 0;
	private SurfaceHolder currentHolder = null;
	
	public final int CAMERA_FACING_BACK = 0;
	public final int CAMERA_FACING_FRONT = 1;
	
	// ��ʼ�����������surfaceCreated�е���
	private void initCamera()
	{
		if (null == mCamera)
			return;
		try {
			if (bIfPreview) {
				mCamera.stopPreview();// stopCamera();
				mCamera.setPreviewCallback(null);
			}
			/* Camera Service settings */
			Camera.Parameters parameters = mCamera.getParameters();
			
			// ��ȡcamera֧�ֵ���ز������ж��Ƿ��������
			List<Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();

			// ��ȡ��ǰ���õķֱ��ʲ���
			int iSettingsWidth = AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL);
			int iSettingsHeight = AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL);
			boolean bSetPreviewSize = false;
			for (int i = 0; i < previewSizes.size(); i++) {
				Size s = previewSizes.get(i);
				if(s.width == iSettingsWidth && s.height == iSettingsHeight) {
					bSetPreviewSize = true;
					parameters.setPreviewSize(iSettingsWidth, iSettingsHeight);
					break;
				}
			}
			parameters.setPreviewFrameRate(25);
			// ָ���ķֱ��ʲ�֧��ʱ����Ĭ�ϵķֱ������
			if(!bSetPreviewSize)
				parameters.setPreviewSize(320, 240);
			
			// ������Ƶ���ݸ�ʽ
			parameters.setPreviewFormat(ImageFormat.NV21);
			// ����������Ч
			try {
				mCamera.setParameters(parameters);
			} catch(Exception e){
				
			}
			// ������Ƶ����ص�������ͨ��AnyChat���ⲿ��Ƶ����ӿڴ���AnyChat�ں˽��д���
			mCamera.setPreviewCallback(new Camera.PreviewCallback() {
				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					if(data.length !=0 && bNeedCapture)
			 			AnyChatCoreSDK.InputVideoData(data, data.length, 0);
				}
			});
			mCamera.startPreview(); // ��Ԥ������
			bIfPreview = true;

			// ��ȡ���ú����ز���
			int pixfmt = -1;
			if(mCamera.getParameters().getPreviewFormat() == ImageFormat.NV21)
				pixfmt = AnyChatDefine.BRAC_PIX_FMT_NV21;
			else if(mCamera.getParameters().getPreviewFormat() == ImageFormat.YV12)
				pixfmt = AnyChatDefine.BRAC_PIX_FMT_YV12;
			else if(mCamera.getParameters().getPreviewFormat() == ImageFormat.NV16)
				pixfmt = AnyChatDefine.BRAC_PIX_FMT_NV16;
			else if(mCamera.getParameters().getPreviewFormat() == ImageFormat.YUY2)
				pixfmt = AnyChatDefine.BRAC_PIX_FMT_YUY2;
			else if(mCamera.getParameters().getPreviewFormat() == ImageFormat.RGB_565)
				pixfmt = AnyChatDefine.BRAC_PIX_FMT_RGB565;
			else
				Log.e(TAG, "unknow camera privew format:" + mCamera.getParameters().getPreviewFormat());
			
			Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_EXTVIDEOINPUT, 1);
			AnyChatCoreSDK.SetInputVideoFormat(pixfmt, previewSize.width, previewSize.height, mCamera.getParameters().getPreviewFrameRate(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	// ����ͷ�ɼ�����
	public void CaptureControl(boolean bCapture) {
		bNeedCapture = bCapture;
	}

	// ��ȡϵͳ������ͷ������
	public int GetCameraNumber() {
		try {
			return Camera.getNumberOfCameras();
		} catch (Exception ex) {
			return 0;
		}
	}
	// �Զ��Խ�
	public void CameraAutoFocus() {
		if(mCamera == null || !bIfPreview)
			return;
		try {		
			mCamera.autoFocus(null);
		} catch (Exception ex) {

		}
	}
	
	// �л�����ͷ
	public void SwitchCamera() {
		try {
			if(Camera.getNumberOfCameras() == 1 || currentHolder == null)
				return;
			iCurrentCameraId = (iCurrentCameraId==0) ? 1 : 0;
			if(null != mCamera)	{
				mCamera.stopPreview(); 
				mCamera.setPreviewCallback(null);
				bIfPreview = false; 
				mCamera.release();
				mCamera = null;     
			}

			mCamera = Camera.open(iCurrentCameraId);
			mCamera.setPreviewDisplay(currentHolder);
			initCamera();
		} catch (Exception ex) {
			 if(null != mCamera) {
				 mCamera.release();
				 mCamera = null;     
			 }
		}
	}
	
	// ѡ������ͷ
	public void SelectVideoCapture(int facing) {
		for (int i = 0; i < Camera.getNumberOfCameras(); i++) 
		{
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == facing) {
				iCurrentCameraId = i;
				break;
			}
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera = Camera.open(iCurrentCameraId);
			currentHolder = holder;
			mCamera.setPreviewDisplay(holder);//set the surface to be used for live preview
			initCamera();
		 } catch (Exception ex) {
			 if(null != mCamera) {
				 mCamera.release();
				 mCamera = null;     
			 }
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(null != mCamera)	{
			try {
				mCamera.stopPreview();
				mCamera.setPreviewCallback(null);
				bIfPreview = false; 
				mCamera.release();
				mCamera = null;    
			} catch (Exception ex) {
				mCamera = null;
				bIfPreview = false; 
			}
		}
		currentHolder = null;
	}

	
	

}
