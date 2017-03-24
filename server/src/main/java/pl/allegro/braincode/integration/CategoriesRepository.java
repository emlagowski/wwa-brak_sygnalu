package pl.allegro.braincode.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.allegro.braincode.integration.allegro.auth.AuthService;
import pl.allegro.braincode.integration.allegro.auth.ServiceGenerator;
import pl.allegro.braincode.integration.allegro.category.CategoriesService;
import pl.allegro.braincode.integration.allegro.category.Category;

import java.io.IOException;
import java.util.List;

@Service
public class CategoriesRepository {

    private final AuthService authService;

    @Autowired
    public CategoriesRepository(AuthService authService) {
        this.authService = authService;
    }

    public List<Category> getCategories() {
        return getCategories(null);
    }

    public List<Category> getCategories(long parentCategoryId) {
        return getCategories(String.valueOf(parentCategoryId));
    }

    public List<Category> getCategories(String parentCategoryId) {
        CategoriesService service = ServiceGenerator.createService(CategoriesService.class);
        try {
            return service
                    .getCategories(authService.auth().getAccessToken(), parentCategoryId)
                    .execute()
                    .body()
                    .getCategories();
        } catch (IOException e) {
            throw new RuntimeException("Error during getting categories", e);
        }
    }

}
