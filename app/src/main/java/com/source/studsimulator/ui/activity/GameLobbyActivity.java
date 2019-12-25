package com.source.studsimulator.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.source.studsimulator.model.entity.Food;
import com.source.studsimulator.model.entity.Friend;
import com.source.studsimulator.model.entity.Hobby;
import com.source.studsimulator.model.entity.Study;
import com.source.studsimulator.model.entity.Work;
import com.source.studsimulator.relation.GameContract;
import com.source.studsimulator.R;
import com.source.studsimulator.model.GameLogic;
import com.source.studsimulator.relation.GamePresenter;
import com.source.studsimulator.ui.entity.PlayerStats;
import com.source.studsimulator.ui.fragments.FoodFragment;
import com.source.studsimulator.ui.fragments.HobbyFragment;
import com.source.studsimulator.ui.fragments.InfoFragment;
import com.source.studsimulator.ui.fragments.StudyFragment;
import com.source.studsimulator.ui.fragments.WorkFragment;

import static com.source.studsimulator.ui.StudSimulatorApplication.getContext;

public class GameLobbyActivity extends AppCompatActivity implements GameContract.View,
        InfoFragment.OnInformationFragmentListener, FoodFragment.OnFoodFragmentListener,
        WorkFragment.OnWorkFragmentListener, StudyFragment.OnStudyFragmentListener,
        HobbyFragment.OnHobbyFragmentListener {

    private GameContract.Presenter presenter = new GamePresenter(this, new GameLogic());

    private TextView moneyTextView;
    private TextView timeTextView;
    private TextView energyTextView;

    private ProgressBar satietyBar;
    private ProgressBar healthBar;
    private ProgressBar educationBar;

    private TextView satietyTextView;
    private TextView healthTextView;
    private TextView educationTextView;

    private ImageButton infoButton;
    private ImageButton foodButton;
    private ImageButton studyButton;
    private ImageButton workButton;
    private ImageButton hobbyButton;

    private Fragment informationFragment;
    private FoodFragment foodFragment;
    private StudyFragment studyFragment;
    private WorkFragment workFragment;
    private HobbyFragment hobbyFragment;

    private FragmentTransaction fragmentTransaction;

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
        energyTextView = findViewById(R.id.energyTextView);

        satietyTextView = findViewById(R.id.satietyText);
        healthTextView = findViewById(R.id.healthText);
        educationTextView = findViewById(R.id.educationText);

        satietyBar = findViewById(R.id.satietyBar);
        healthBar = findViewById(R.id.healthBar);
        educationBar = findViewById(R.id.educationBar);
    }

    @Override
    public void refreshPlayerStats(PlayerStats playerStats) {
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
        presenter.clickOnNewWeekButton(Integer.valueOf(String.valueOf(energyTextView.getText())));
    }

    @Override
    public void clickOnFoodButton(int position) {
        presenter.clickOnFoodButton(position);
    }

    @Override
    public void unclickFoodButton(Food food) {
        presenter.unclickOnFoodButton(food);
    }

    @Override
    public void clickOnHobbyButton(int position, Friend friend) {
        presenter.clickOnHobbyButton(position, friend);
    }

    @Override
    public void unclickOnHobbyButton(Hobby hobby, Friend friend) {
        presenter.unclickOnHobbyButton(hobby, friend);
    }

    @Override
    public void unClick(Work workItem, Work.TypeOfWork type) {
        workFragment.unClick(workItem, type);
    }

    @Override
    public void activateWorkButton(int number, Work.TypeOfWork type) {
        workFragment.activateButton(number, type);
    }

    @Override
    public void activateStudyButton(int number, Study.TYPE_OF_STUDY type) {
        studyFragment.activateButton(number, type);
    }

    @Override
    public void activateFoodButton(int number) {
        foodFragment.activateButton(number);
    }

    @Override
    public void activateHobbyButton(int number) {
        hobbyFragment.activateButton(number);
    }

    @Override
    public void notAvailableMessage(String message) {
        Toast.makeText(getContext(),
                message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clickOnWorkButton(int number, Work.TypeOfWork type) {
        presenter.clickOnWorkButton(number, type);
    }

    @Override
    public void unclickOnWorkButton(Work work) {
        presenter.unclickOnWorkButton(work);
    }

    @Override
    public void clickOnStudyButton(int position, Study.TYPE_OF_STUDY type) {
        presenter.clickOnStudyButton(position, type);
    }

    @Override
    public void unclickOnStudyButton(Study study) {
        presenter.unclickOnStudyButton(study);
    }

    @Override
    public void updateWeek(int weekNumber) {
        timeTextView.setText(String.format(getString(R.string.weekNumber), weekNumber));
    }

    @Override
    public void updateEnergyLevel(int energyLevel) {
        energyTextView.setText(String.valueOf(energyLevel));
    }

    @Override
    public void updateWeekInformation(GamePresenter.PlayerInformation newInformation) {
        ((InfoFragment) informationFragment).updateWeekInformation(newInformation);
    }

    @Override
    public void cleanRandomActionsMessages() {
        InfoFragment infoFragment = (InfoFragment) informationFragment;
        infoFragment.cleanView();
    }

    @Override
    public void writeRandomActionMessage(String message) {
        InfoFragment infoFragment = (InfoFragment) informationFragment;
        infoFragment.writeMessage(message);
    }

    @Override
    public void updateFragmentSkills(int programming, int english) {
        ((WorkFragment) workFragment).updateSkills(programming, english);
        ((StudyFragment) studyFragment).updateSkills(programming, english);
    }

    public void showGameEndMessage(String title, String message, String button_name, int audio) {
        SoundActivity.hearSound(this, audio);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(
                        button_name,
                        (dialog, id) -> {
                            finish();
                            dialog.cancel();
                        });
        AlertDialog alert = builder.create();
        alert.show();

        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
    }

    private void setOnClickListenersForFragmentButtons() {
        infoButton.setOnClickListener(v -> replaceFragment(informationFragment));

        foodButton.setOnClickListener(v -> replaceFragment(foodFragment));

        studyButton.setOnClickListener(v -> replaceFragment(studyFragment));

        workButton.setOnClickListener(v -> replaceFragment(workFragment));

        hobbyButton.setOnClickListener(v -> replaceFragment(hobbyFragment));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
