<script setup>
// @EXERCISE: If user is authenticated redirect to the requested URL.
// @EXERCISE: If user is not authenticated, keep the requested URL and after a successful authentication redirect to the requested resource.
import { onBeforeMount, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const { setUserData, persistUserData, isAuthenticated } = useApplicationStore();

const loading = ref(false);
const credentials = ref({
    username: '',
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    afm: '',
    idNumber: '',
    role: ["User"]
});
const errorMessage = ref('');

const onRoleChange = (event) => {
  credentials.role = event.target.checked ? ["Owner"] : ["User"];
};

const onFormSubmit = () => {
    loading.value = true;
    errorMessage.value = '';
    console.log(credentials);
    fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials.value)
    })
        .then(async (response) => {
          if (response.ok) {
            response.json().then((data) => {
              setUserData(data);
              persistUserData();
              router.push({name: 'login'});
            });
          } else {
            const error = await response.json();
            errorMessage.value = error.message || 'An error occurred during registration.';
          }
        })
        .catch((err) => {
            console.warn(err);
            errorMessage.value = 'An unexpected error occurred';
        })
        .finally(() => {
            loading.value = false;
        });
};

onBeforeMount(() => {
    if (isAuthenticated === true) {
        router.push({ name: 'home' });
    }
});
</script>

<template>
    <div class="bg-body-tertiary">
        <div class="container">
            <div class="row py-4 px-3">
                <div class="col-4">
                    <div class="mb-4">
                        <h1 class="fs-3">Sign Up</h1>
                    </div>
                    <div class="spinner-border" role="status" v-if="loading">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                    <form v-else @submit="onFormSubmit">
                        <div class="mb-2" v-if="errorMessage">
                            <div class="alert alert-danger" role="alert">
                                {{errorMessage}}
                            </div>
                        </div>
                        <div class="mb-2">
                            <label for="usernameFormControl" class="form-label mb-1"
                                >Username</label
                            >
                            <input
                                v-model="credentials.username"
                                type="text"
                                class="form-control"
                                id="usernameFormControl"
                                required
                            />
                        </div>
                        <div class="mb-2">
                          <label for="emailFormControl" class="form-label mb-1"
                          >Email address</label
                          >
                          <input
                              v-model="credentials.email"
                              type="text"
                              class="form-control"
                              id="emailFormControl"
                              required
                          />
                        </div>
                        <div class="mb-2">
                            <label for="passwordFormControl" class="form-label mb-1"
                                >Password</label
                            >
                            <input
                                v-model="credentials.password"
                                type="password"
                                class="form-control"
                                id="passwordFormControl"
                            />
                        </div>
                      <div class="mb-2">
                        <label for="firstNameFormControl" class="form-label mb-1"
                        >First Name</label
                        >
                        <input
                            v-model="credentials.firstName"
                            type="text"
                            class="form-control"
                            id="firstNameFormControl"
                            required
                        />
                      </div>
                      <div class="mb-2">
                        <label for="lastNameFormControl" class="form-label mb-1"
                        >Last Name</label
                        >
                        <input
                            v-model="credentials.lastName"
                            type="text"
                            class="form-control"
                            id="lastNameFormControl"
                            required
                        />
                      </div>
                      <div class="mb-2">
                        <label for="phoneNumberFormControl" class="form-label mb-1"
                        >Phone Number</label
                        >
                        <input
                            v-model="credentials.phoneNumber"
                            type="text"
                            class="form-control"
                            id="phoneNumberFormControl"
                            required
                        />
                      </div>
                      <div class="mb-2">
                        <label for="AFMFormControl" class="form-label mb-1"
                        >AFM</label
                        >
                        <input
                            v-model="credentials.afm"
                            type="text"
                            class="form-control"
                            id="AFMFormControl"
                            required
                        />
                      </div>
                      <div class="mb-2">
                        <label for="idNumberFormControl" class="form-label mb-1"
                        >ID Number</label
                        >
                        <input
                            v-model="credentials.idNumber"
                            type="text"
                            class="form-control"
                            id="idNumberFormControl"
                            required
                        />
                      </div>
                      <div>
                        <label>
                          <input type="checkbox" @change="onRoleChange"/>
                          Role:
                        </label>
                        <p>Checkbox value: {{ credentials.role }}</p>
                      </div>
                        <button @click="onFormSubmit" type="submit" class="btn btn-primary">
                            Sign Up
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>
