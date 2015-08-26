package course.labs.fragmentslab;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

		// If the layout is single-pane, create the FriendsFragment
		// and add it to the Activity
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        mFriendsFragment = new FriendsFragment();
		if (!isInTwoPaneMode()) {

			// mFriendsFragment = new FriendsFragment();

			//TODO 1 - add the FriendsFragment to the fragment_container
			// mFriendsFragment = (FriendsFragment) getFragmentManager().findFragmentById(R.id.friends_frag);
            // FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, mFriendsFragment);
            ft.commit();

		} else {
            ft.replace(R.id.friends_frag, mFriendsFragment);
			// Otherwise, save a reference to the FeedFragment for later use
			// mFeedFragment = (FeedFragment) getFragmentManager().findFragmentById(R.id.feed_frag);
            FeedFragment mFeedFragment = new FeedFragment();
            ft.add(R.id.feed_frag, mFeedFragment);
            ft.commit();
		}
	}

	// If there is no fragment_container ID, then the application is in
	// two-pane mode

	private boolean isInTwoPaneMode() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        } else {
            return false;
        }
		// return findViewById(R.id.fragment_container) == null;
	}

	// Display selected Twitter feed

	public void onItemSelected(int position) {

		Log.i(TAG, "Entered onItemSelected(" + position + ")");

        FragmentTransaction ft = getFragmentManager().beginTransaction();

		// If there is no FeedFragment instance, then create one
		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// If in single-pane mode, replace single visible Fragment

		if (!isInTwoPaneMode()) {

			//TODO 2 - replace the fragment_container with the FeedFragment
			// mFeedFragment = (FeedFragment) getFragmentManager().findFragmentById(R.id.feed_frag);
            // FragmentTransaction ft = getFragmentManager().beginTransaction();
            mFeedFragment = new FeedFragment();
            // add to backStack allows to get back to fragment on pressing the back button
            ft.replace(R.id.fragment_container, mFeedFragment).addToBackStack("tag");
            ft.commit();

			// execute transaction now
			getFragmentManager().executePendingTransactions();

		} else {
            ft.replace(R.id.feed_frag, mFeedFragment);

            // Otherwise, save a reference to the FeedFragment for later use
            // mFeedFragment = (FeedFragment) getFragmentManager().findFragmentById(R.id.feed_frag);
            //ft.commit();

        }

		// Update Twitter feed display on FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}

}
