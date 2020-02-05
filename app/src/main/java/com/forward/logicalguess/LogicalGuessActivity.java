package com.forward.logicalguess;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicalGuessActivity extends Activity {
    private static final int DATA_SIZE = 4;
    private int[] mData;
    private Random mRandom;
    private List<String> mResultDatas;
    private ResultAdapter mAdapter;

    private ListView mListView;
    private EditText mInput;
    private TextView mStep;
    private boolean mIsSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logical_guess);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        mInput = (EditText) findViewById(R.id.input);
        mStep = (TextView) findViewById(R.id.step);
    }

    private void initData() {
        mData = new int[DATA_SIZE];
        mRandom = new Random();
        mResultDatas = new ArrayList<>();
        mAdapter = new ResultAdapter(this, mResultDatas);
        mListView.setAdapter(mAdapter);
        start();
    }

    private void initListener() {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.restart:
                start();
                break;
            case R.id.history:
                Toast.makeText(LogicalGuessActivity.this, R.string.not_support, Toast.LENGTH_SHORT).show();
                break;
            case R.id.display:
                display();
                break;
            case R.id.confirm:
                confirm();
                break;
        }
    }

    private void display() {
        ConfirmDialog.getInstance(this).showDialog(
                getString(R.string.peek_the_answer, getDataString()),
                getString(R.string.cancel),
                getString(R.string.restart), new OnConfirmListener() {
                    @Override
                    public void cancel(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void confirm(Dialog dialog) {
                        start();
                        dialog.dismiss();
                    }
                });
    }

    private void start() {
        productData();
        mStep.setText("0");
        mIsSuccess = false;
    }

    private void confirm() {
        if (mIsSuccess) {
            ConfirmDialog.getInstance(this).showDialog(
                    getString(R.string.guess_success_restart),
                    getString(R.string.cancel),
                    getString(R.string.restart), new OnConfirmListener() {
                        @Override
                        public void cancel(Dialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void confirm(Dialog dialog) {
                            start();
                            dialog.dismiss();
                        }
                    });
        } else {
            String input = mInput.getText().toString();
            if (!TextUtils.isEmpty(input) && input.length() == DATA_SIZE && !isRepeat(input)) {
                GuessEntity guessEntity = getGuessEntity(input);
                mResultDatas.add(0, guessEntity.toResult());
                mAdapter.refreshList();
                mStep.setText(String.valueOf(mResultDatas.size()));
                if (guessEntity.isSuccess()) {
                    mIsSuccess = true;
                    ConfirmDialog.getInstance(this).showDialog(
                            getString(R.string.congratulations_you_success),
                            getString(R.string.cancel),
                            getString(R.string.restart), new OnConfirmListener() {
                                @Override
                                public void cancel(Dialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void confirm(Dialog dialog) {
                                    start();
                                    dialog.dismiss();
                                }
                            });
                }
            } else {
                Toast.makeText(this, R.string.please_input_four_num, Toast.LENGTH_SHORT).show();
            }
        }
        mInput.setText("");
    }

    private void productData() {
        mResultDatas.clear();
        mAdapter.refreshList();
        for (int i = 0; i < DATA_SIZE; i++) {
            mData[i] = -1;
        }
        for (int i = 0; i < DATA_SIZE; i++) {
            while (true) {
                int random = mRandom.nextInt(10);
                if (!isExit(random)) {
                    mData[i] = random;
                    break;
                }
            }
        }
    }

    private boolean isExit(int num) {
        for (int i = 0; i < DATA_SIZE; i++) {
            if (mData[i] == -1) {
                return false;
            } else if (mData[i] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isRepeat(String data) {
        for (int i = 0; i < DATA_SIZE; i++) {
            for (int j = i + 1; j < DATA_SIZE; j++) {
                if (data.charAt(i) == data.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getDataString() {
        String s = "";
        for (int i = 0; i < DATA_SIZE; i++) {
            s = s.concat(String.valueOf(mData[i]));
        }
        return s;
    }

    private GuessEntity getGuessEntity(String data) {
        GuessEntity entity = new GuessEntity();
        if (data.equals(getDataString())) {
            entity.setData(data);
            entity.setNumA(4);
            entity.setNumB(0);
            entity.setSuccess(true);
        } else {
            int a = 0;
            int b = 0;
            for (int i = 0; i < DATA_SIZE; i++) {
                String str = String.valueOf(mData[i]);
                if (str.equals(String.valueOf(data.charAt(i)))) {
                    a++;
                } else if (data.contains(str)) {
                    b++;
                }
            }
            entity.setData(data);
            entity.setNumA(a);
            entity.setNumB(b);
        }
        return entity;
    }
}
