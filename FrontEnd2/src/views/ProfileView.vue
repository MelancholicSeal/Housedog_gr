<script setup>
import { useApplicationStore } from '@/stores/application.js';
import {onMounted, ref} from "vue";
import {useRemoteData} from "@/composables/useRemoteData.js";
const { userData } = useApplicationStore();

const backendEnvVar = import.meta.env.VITE_BACKEND;

const urlRef = ref(backendEnvVar + '/api/users/' + userData.id);
const authRef = ref(true);
const data = ref(null);
const {loading, performRequest: getUserData} = useRemoteData(urlRef, authRef,data);

onMounted(() => {
  getUserData();
});

</script>

<template>
    <div class="bg-body-tertiary">
        <div class="container">
            <div class="row py-4 px-3">
                <div class="col-12">
                    <div class="mb-4">
                        <h1 class="fs-3">Profile</h1>
                    </div>
                    <div>
                        <!-- @EXERCISE: Create a nice component to present user data -->
                        
                <div class="card" style="width: 18rem;">
                  <div class="card-body">
                    <h5 class="card-title">{{ data.username }}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">Details</h6>
                    <p class="card-text"> First Name: {{ data.firstName }}</p>
                    <p class="card-text"> Last Name: {{ data.lastName }}</p>
                    <p class="card-text"> Email: {{ data.email }}</p>
                    <p class="card-text"> Phone Number: {{ data.phoneNumber }}</p>
                    <p v-if="data.role === 'ROLE_USER'" class="card-text"> Role: User</p>
                    <p v-else-if="data.role === 'ROLE_OWNER'" class="card-text"> Role: Owner</p>
                    <p v-else class="card-text"> Role: Admin</p>
                  </div>
                </div>
                <!--<pre>{{ userData }}</pre>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
