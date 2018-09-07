package com.an.viewpagerexample1.fragments;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.an.viewpagerexample1.R;

public class PagerFragment extends Fragment {

    //constants
    private static final String TAG = PagerFragment.class.getSimpleName();
    private static final String ARG_PARAM_1 = "passed_type";

    // UI Components
    private ImageView mIvMainImage;
    private VideoView mVvMainVideo;

    // Other objects
    private Activity mActivity;
    private String mPassedType;

    public static PagerFragment newInstance(String visitPlanId) {
        PagerFragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM_1, visitPlanId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
        Log.d(TAG, "OnCreateView -> PagerFragment loads");

        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIvMainImage = view.findViewById(R.id.fragment_pager_iv_main_image);
        mVvMainVideo = view.findViewById(R.id.fragment_pager_vv_main_video);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume() on fragment: PagerFragment");

        if (getArguments() != null) {
            Log.d(TAG, "Passed type: " + getArguments().getString(ARG_PARAM_1));

            mPassedType = getArguments().getString(ARG_PARAM_1);
        }

        if (mPassedType.equalsIgnoreCase("IMG_1")) {
            mIvMainImage.setVisibility(View.VISIBLE);
            mVvMainVideo.setVisibility(View.GONE);

            mIvMainImage.setImageResource(R.drawable.image_1);
        } else if (mPassedType.equalsIgnoreCase("IMG_2")) {
            mIvMainImage.setVisibility(View.VISIBLE);
            mVvMainVideo.setVisibility(View.GONE);

            mIvMainImage.setImageResource(R.drawable.image_2);
        } else if (mPassedType.equalsIgnoreCase("VDO_1")) {
            mIvMainImage.setVisibility(View.GONE);
            mVvMainVideo.setVisibility(View.VISIBLE);

            String path = "android.resource://" + mActivity.getPackageName() + "/" + R.raw.video_1;
            videoViewWithControls(mVvMainVideo, path);
        } else if (mPassedType.equalsIgnoreCase("VDO_2")) {
            mIvMainImage.setVisibility(View.GONE);
            mVvMainVideo.setVisibility(View.VISIBLE);

            String path = "android.resource://" + mActivity.getPackageName() + "/" + R.raw.video_2;
            videoViewWithControls(mVvMainVideo, path);
        }
    }

    private void videoViewWithControls(final VideoView videoView, String path) {
        final MediaController mediacontroller = new MediaController(mActivity);
        mediacontroller.setAnchorView(videoView);

        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(Uri.parse(path));
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        videoView.setMediaController(mediacontroller);
                        mediacontroller.setAnchorView(videoView);

                    }
                });
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(mActivity, "Video over", Toast.LENGTH_SHORT).show();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
    }

    public void pausePlay() {
        mVvMainVideo.pause();
    }

    public void autoStart() {
        if (mVvMainVideo.isPlaying()) {
            mVvMainVideo.resume();
        } else {
            mVvMainVideo.start();
        }
    }

}
