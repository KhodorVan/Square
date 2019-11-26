package com.source.studsimulator.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.source.studsimulator.model.entity.Food;
import com.source.studsimulator.model.entity.Study;
import com.source.studsimulator.model.entity.Work;
import com.source.studsimulator.relation.GameContract;
import com.source.studsimulator.R;
import com.source.studsimulator.model.GameLogic;
import com.source.studsimulator.relation.GamePresenter;
import com.source.studsimulator.ui.fragments.FoodFragment;
import com.source.studsimulator.ui.fragments.HobbyFragment;
import com.source.studsimulator.ui.fragments.InfoFragment;
import com.source.studsimulator.ui.fragments.StudyFragment;
import com.source.studsimulator.ui.fragments.WorkFragment;

public class GameLobbyActivity extends AppCompatActivity implements GameContract.View,
        InfoFragment.OnInformationFragmentListener, FoodFragment.OnFoodFragmentListener,
        WorkFragment.OnWorkFragmentListener, StudyFragment.OnStudyFragmentListener {

    private GameContract.Presenter presenter = new GamePresenter(this, new GameLogic());

    private TextView moneyTextView;
    private TextView timeTextView;

    ProgressBar satietyBar;
    ProgressBar healthBar;
    ProgressBar educationBar;

    TextView satietyTextView;
    TextView healthTextView;
    TextView educationTextView;

    ImageButton infoButton;
    ImageButton foodButton;
    ImageButton studyButton;
    ImageButton workButton;
    ImageButton hobbyButton;

    Fragment informationFragment;
    Fragment foodFragment;
    Fragment studyFragment;
    Fragment workFragment;
    Fragment hobbyFragment;

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_lobby_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        informationFragment = new InfoFragment();
        foodFragment = new FoodFragment();
        studyFragment = new StudyFragment();
        workFragment = new WorkFragment();
        hobbyFragment = new HobbyFragment();

        infoButton = findViewById(R.id.infoButton);
        foodButton = findViewById(R.id.foodButton);
        studyButton = findViewById(R.id.studyButton);
        workButton = findViewById(R.id.workButton);
        hobbyButton = findViewById(R.id.hobbyButton);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentLayout, informationFragment).commit();

        setOnClickListenersForFragmentButtons();

        initPlayerStatsView();
        presenter.viewCreated();
    }

    private void initPlayerStatsView() {
        moneyTextView = findViewById(R.id.moneyCount);
        timeTextView = findViewById(R.id.actualTime);

        satietyTextView = findViewById(R.id.satietyText);
        healthTextView = findViewById(R.id.healthText);
        educationTextView = findViewById(R.id.educationText);

        satietyBar = findViewById(R.id.satietyBar);
        healthBar = findViewById(R.id.healthBar);
        educationBar = findViewById(R.id.educationBar);
    }

    @Override
    public void refreshPlayerStats(GamePresenter.PlayerStatsObject playerStats) {
        moneyTextView.setText(playerStats.getMoney());

        satietyBar.setProgress(playerStats.getSatiety());
        healthBar.setProgress(playerStats.getHealth());
        educationBar.setProgress(playerStats.getEducationLevel());

        satietyTextView.setText(String.valueOf(playerStats.getSatiety()));
        healthTextView.setText(String.valueOf(playerStats.getHealth()));
        educationTextView.setText(String.valueOf(playerStats.getEducationLevel()));
    }

    private void replaceFragment(Fragment fragment) {
        if (getSupportFragmentManager().findFragmentById(R.id.fragmentLayout) != fragment) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentLayout, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onNewWeekClicked() {
        presenter.clickOnNewWeekButton();
    }

    @Override
    public void clickOnFoodButton(Food food) {
        presenter.clickOnFoodButton(food);
    }

    @Override
    public void clickOnWorkButton(Work work) {
        presenter.clickOnWorkButton(work);
    }

    @Override
    public void clickOnStudyButton(Study study) {
        presenter.clickOnLearnButton(study);
    }

    @Override
    public void updateWeek(int weekNumber) {
        timeTextView.setText(String.format(getString(R.string.weekNumber), weekNumber));
    }

    private void setOnClickListenersForFragmentButtons() {
        infoButton.setOnClickListener(v -> replaceFragment(informationFragment));

        foodButton.setOnClickListener(v -> replaceFragment(foodFragment));

        studyButton.setOnClickListener(v -> replaceFragment(studyFragment));

        workButton.setOnClickListener(v -> replaceFragment(workFragment));

        hobbyButton.setOnClickListener(v -> replaceFragment(hobbyFragment));
    }
}
