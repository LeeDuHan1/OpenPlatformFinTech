package android.bfop.kftc.com.useorgsampleapprenewal.layout;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    public MainFragment() {} // Required empty public constructor

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String actionBarTitle) {

        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ACTIONBAR_TITLE, actionBarTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        super.initBaseFragment(view); // BaseFragment 초기화 수행
        mainActivity.showBackArrowOnActionBar(false);

        // 버튼 이벤트핸들러 바인딩
        bindButtonClickEvents(view);

        return view;
    }

    /**
     * 버튼 이벤트핸들러 바인딩
     *
     * @param view
     */
    public void bindButtonClickEvents(View view){

        ((Button)view.findViewById(R.id.btnAuthNewMenu)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnAuthOldAppMenu)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnAuthOldWebMenu)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnAPICallMenu)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.btnSettings)).setOnClickListener(this);
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        Fragment fm = null;
        String title = null;

        int btnId = v.getId();
        MainActivity mainActivity = ((MainActivity)getActivity());

        switch(btnId){
            case R.id.btnAuthNewMenu:
                break;
            case R.id.btnAuthOldAppMenu:
                break;
            case R.id.btnAuthOldWebMenu:
                break;
            case R.id.btnAPICallMenu:
                break;
            case R.id.btnSettings:
                break;
            default:
                break;
        }

        mainActivity.goPage(btnId);

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
     * 뒤로가기 눌렀을 때 동작
     */
    @Override
    public void doBackBehavior() {

        mainActivity.getBackPressCloseHandler().onBackPressed();
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
        void onFragmentInteractionMainPage(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionMainPage(uri);
        }
    }


}
