package com.susion.boring.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.susion.boring.R;
import com.susion.boring.mainui.MainActivity;
import com.susion.boring.music.activity.SearchMusicActivity;
import com.susion.boring.utils.UIUtils;

/**
 * Created by susion on 17/1/20.
 */
public class SearchBar extends LinearLayout {

    private Context mContext;
    private EditText mEtText;
    private Button mBtSearch;

    private boolean mJumpToSearchPage;

    private OnSearchButtonClickListener listener;

    public SearchBar(Context context) {
        super(context);
        init(context);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View.inflate(mContext, R.layout.view_search_bar, this);
        mEtText = (EditText) findViewById(R.id.search_bar_tv_text);
        mBtSearch = (Button) findViewById(R.id.search_bar_bt_search);

        initListener();
        initView();
        
    }

    private void initView() {
        setTextViewHintContent();
     }

    private void setTextViewHintContent() {
        Drawable searchDrawable = mContext.getResources().getDrawable(R.drawable.search_bar_search);
        searchDrawable.setBounds(UIUtils.dp2Px(15), 0, UIUtils.dp2Px(35), UIUtils.dp2Px(20));
        mEtText.setCompoundDrawables(searchDrawable, null, null, null);
        mEtText.setCompoundDrawablePadding(UIUtils.dp2Px(25));

        mEtText.setHint("搜索音乐");
        mEtText.setHintTextColor(getResources().getColor(R.color.colorSecondaryText));
    }

    private void initListener() {
        mEtText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mJumpToSearchPage) {

                    Intent intent = new Intent();
                    intent.setClass(mContext, SearchMusicActivity.class);
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation((MainActivity) mContext, SearchBar.this,
                                    mContext.getString(R.string.transition_name_search_bar));
                    mContext.startActivity(intent, options.toBundle());

                    return;
                }

                // call system keyboard

            }
        });


        mBtSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mEtText.getText().toString();

                if (TextUtils.isEmpty(searchText)) {
                    return;
                }

                if (listener != null) {
                    listener.doSearch(searchText);
                }


            }
        });
    }

    public void setJumpToSearchPage(boolean JumpToSearchPage) {
        this.mJumpToSearchPage = JumpToSearchPage;

        if (!mJumpToSearchPage) {
            mEtText.setFocusable(true);
            mEtText.setFocusableInTouchMode(true);
            mEtText.requestFocus();
        }
    }

    public void setBackground(int colorId){
        setBackgroundColor(getResources().getColor(colorId));
    }

    public void setSearchButtonVisible(int visible){
        mBtSearch.setVisibility(visible);
    }

    public interface OnSearchButtonClickListener{
        void doSearch(String searchContent);
    }

    public void setSearchButtonClickListener(OnSearchButtonClickListener listener) {
        this.listener = listener;
    }
}
