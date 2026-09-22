package stepdefinitions;

import Pages.WavesPage;
import io.cucumber.java.en.Then;

public class WaveOperations {
    @Then("user processes the wave")
    public void userProcessesTheWave() throws InterruptedException {
        WavesPage wavesPage = new WavesPage();
        wavesPage.processWave();
    }
}
