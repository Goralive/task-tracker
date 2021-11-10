package com.tasktracker.suites;

import com.tasktracker.repository.entity.UserEntity;
import com.tasktracker.service.user.UserTO;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserTestSuite extends BaseTestSuite {

    public static final String USERS = "/users";
    public static final UserTO ADMIN_TO = new UserTO(null, "admin", "admin@googl.com", false);

    @Test
    void shouldNotFound() throws Exception {
        String actual = mockMvc.perform(get(USERS + "/{id}", 7)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        assertThat(actual).isEqualTo("User not found with id '7'");
    }

    @Test
    void shouldGetUsersBeEmpty() throws Exception {
        mockMvc.perform(get(USERS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        shouldGetUsersBeEmpty();

        MvcResult actual = mockMvc.perform(post(USERS)
                        .content(asJson(ADMIN_TO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserEntity responseUserTo = fromResponse(actual, UserEntity.class);

        SoftAssertions.assertSoftly(s -> {
            s.assertThat(responseUserTo.getId()).isNotNull();
            s.assertThat(responseUserTo.getEmail()).isEqualTo(ADMIN_TO.email);
            s.assertThat(responseUserTo.getName()).isEqualTo(ADMIN_TO.name);
            s.assertThat(responseUserTo.isDeleted()).isEqualTo(false);
        });

        MvcResult getAllUsersResponse = mockMvc.perform(get(USERS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserEntity[] usersFromResponse = fromResponse(getAllUsersResponse, UserEntity[].class);
        assertThat(usersFromResponse).hasSize(1);

        UserEntity createdUser = usersFromResponse[0];

        SoftAssertions.assertSoftly(u -> {
            u.assertThat(createdUser.getId()).isNotNull();
            u.assertThat(responseUserTo.getEmail()).isEqualTo(ADMIN_TO.email);
            u.assertThat(responseUserTo.getName()).isEqualTo(ADMIN_TO.name);
            u.assertThat(responseUserTo.isDeleted()).isEqualTo(false);
        });
    }


    @Test
    void shouldDeleteUserTest() throws Exception {
        shouldGetUsersBeEmpty();
        UserEntity user = createUser();

        MvcResult userCreatedResponse = mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        UserEntity[] actualUserArray = fromResponse(userCreatedResponse, UserEntity[].class);

        assertThat(actualUserArray)
                .hasSize(1)
                .usingRecursiveComparison()
                .isEqualTo(user);

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldUpdateUserTest() throws Exception {
        shouldGetUsersBeEmpty();
        UserEntity user = createUser();

        UserTO update = new UserTO(7L,"Update User", "Update@user.com");
        MvcResult result = mockMvc.perform(put("/users/{id}", user.getId())
                        .content(asJson(update))
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andReturn();
        UserEntity userFromResponse = fromResponse(result, UserEntity.class);

        assertThat(userFromResponse.getName()).isEqualTo(update.name);
        assertThat(userFromResponse.getEmail()).isEqualTo(update.email);
        assertThat(userFromResponse.isDeleted()).isEqualTo(user.isDeleted());
        assertThat(userFromResponse.getId()).isEqualTo(user.getId());

        MvcResult resultAfterUpdate = mockMvc.perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        UserEntity[] allUsersAfterSave = fromResponse(resultAfterUpdate, UserEntity[].class);

        assertThat(allUsersAfterSave).hasSize(1);
        assertThat(allUsersAfterSave[0])
                .usingRecursiveComparison()
                .isEqualTo(userFromResponse);
    }
}
