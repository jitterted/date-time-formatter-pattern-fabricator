package com.jitterted.formathero.datetimeformatter.adapter.in.web;

import com.jitterted.formathero.datetimeformatter.SomeZonedDateTimes;
import com.jitterted.formathero.datetimeformatter.adapter.out.caffeine.CaffeineFabricatorRepository;
import com.jitterted.formathero.datetimeformatter.application.FabricatorService;
import com.jitterted.formathero.datetimeformatter.application.port.FabricatorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FabricatorControllerTest {

    @Test
    public void uponStartPatternAndExampleAreEmptyStrings() throws Exception {
        FabricatorRepository fabricatorRepository = new CaffeineFabricatorRepository();
        FabricatorService fabricatorService = new FabricatorService(fabricatorRepository, List.of(SomeZonedDateTimes.JAN_9_2031));
        FabricatorController fabricatorController = new FabricatorController(fabricatorService);

        ConcurrentModel model = new ConcurrentModel();
        String pageName = fabricatorController.mainView(model, "windy-dolphin-73");

        assertThat(pageName)
                .isEqualTo("fabricator");

        assertThat((String) model.getAttribute("pattern"))
                .isEmpty();
        List<String> examples = (List<String>) model.getAttribute("examples");
        assertThat(examples)
                .containsExactly("");
    }

    @Test
    public void add_4_Digit_Year_PatternIsYyyy() throws Exception {
        FabricatorRepository fabricatorRepository = new CaffeineFabricatorRepository();
        FabricatorService fabricatorService = new FabricatorService(
                fabricatorRepository,
                List.of(SomeZonedDateTimes.JAN_9_2031));
        FabricatorController fabricatorController =
                new FabricatorController(fabricatorService);
        String id = "blue-penguin-22";

        String page = fabricatorController.fabricate("yyyy", id);

        assertThat(page)
                .isEqualTo("redirect:/?id=" + id);

        ConcurrentModel model = new ConcurrentModel();
        fabricatorController.mainView(model, id);
        assertThat((String) model.getAttribute("pattern"))
                .isEqualTo("yyyy");
        List<String> examples = (List<String>) model.getAttribute("examples");
        assertThat(examples)
                .containsExactly("2031");
    }

}