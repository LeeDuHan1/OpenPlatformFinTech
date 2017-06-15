package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.App;
import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements Button.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RadioGroup rg_svr;
    private EditText m_etAppKey;
    private EditText m_etAppSecret;
    private EditText m_etWebCallbackUrl;
    private EditText m_etCallbackUrl;
    private Spinner m_spScope;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {

        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        MainActivity mainActivity = ((MainActivity)this.getActivity());

        // 버튼 이벤트핸들러 바인딩
        bindButtonClickEvents(view);

        // 액션바에 뒤로가기 버튼 노출하기
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rg_svr = (RadioGroup) view.findViewById(R.id.rgSvr);
        m_etAppKey = (EditText) view.findViewById(R.id.etAppKey);
        m_etAppSecret = (EditText) view.findViewById(R.id.etAppSecret);
        m_etWebCallbackUrl = (EditText) view.findViewById(R.id.etWebCallbackUrl);
        m_etCallbackUrl = (EditText) view.findViewById(R.id.etCallbackUrl);
        m_spScope = (Spinner) view.findViewById(R.id.spScope);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mainActivity, R.array.sp_scope_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m_spScope.setAdapter(adapter);

        loadPreferences(App.getEnv());


        return view;
    }

    /**
     * 버튼 이벤트핸들러 바인딩
     *
     * @param view
     */
    public void bindButtonClickEvents(View view){

        ((Button)view.findViewById(R.id.btnSaveSettings)).setOnClickListener(this); // 저장 버튼
        ((Button)view.findViewById(R.id.btnResetSettings)).setOnClickListener(this); // 초기화 버튼
        ((Button)view.findViewById(R.id.btnRemoveSession)).setOnClickListener(this); // 세션쿠키제거 버튼
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnAuthOldWeb:
                break;
            case R.id.btnRegAcntOldWeb:
                break;
            case R.id.btnAuthAcntOldWeb:
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *
     * TODO: 얘 꼭 필요해??
     *
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionSettings(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionSettings(uri);
        }
    }

    /**
     * input form 에 SharedPreferences 값 로딩
     */
    private void loadPreferences(String env) {

        setRadioSvrCheckedFromPref(env);

        String es = App.getEnvSuffix(env);

        m_etAppKey.setText(StringUtil.getPropString("APP_KEY" + es));
        m_etAppSecret.setText(StringUtil.getPropString("APP_SECRET" + es));
        m_etWebCallbackUrl.setText(StringUtil.getPropString("WEB_CALLBACK_URL" + es));
        m_etCallbackUrl.setText(StringUtil.getPropString("APP_CALLBACK_URL" + es));
        m_spScope.setSelection(StringUtil.getPropString("SCOPE" + es).equals("inquiry") ? 0 : 1);
    }

    /**
     * 매개변수로 호출서버 구분자를 받아서 라디오버튼 클릭상태 반영
     */
    private void setRadioSvrCheckedFromPref(String env){

        int id = 0;
//        String env = App.getEnv(); // 파라미터로 받는 걸로 변경
        switch(env){
            case Constants.ENV_TEST: id = R.id.radioSvr_TEST; break;
            case Constants.ENV_PRD: id = R.id.radioSvr_PRD; break;
        }
        rg_svr.check(id);
    }

}