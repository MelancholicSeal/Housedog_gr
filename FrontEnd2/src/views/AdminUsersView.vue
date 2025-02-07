<script setup>
import {onMounted, ref, watch} from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
import {useApplicationStore} from "@/stores/application.js";
const backendEnvVar = import.meta.env.VITE_BACKEND;
const { userData } = useApplicationStore();

const urlRef = ref(backendEnvVar + '/api/users');
const urlRole = ref(backendEnvVar + '/api/users/' + userData.id);
const authRef = ref(true);
const data = ref(null);
const {loading, performRequest: UserData } = useRemoteData(urlRef, authRef,data);
const role_data = ref(null);
const {loading2, performRequest: UserRoleData } = useRemoteData(urlRole, authRef, role_data);

onMounted(() => {
  UserData();
  UserRoleData();
  console.log(data);
});

const applicationStore = useApplicationStore();
const userRoles = applicationStore.userData.roles;
console.log(userRoles);

const onSubmit = (id) => {
  const urlRefDelete = ref(backendEnvVar + '/api/users/' + id);
  const methodRef = ref('DELETE');
  const {loading5, performRequest: DeleteUser } = useRemoteData(urlRefDelete, authRef, data, methodRef);
  DeleteUser()
      .then((res) => {
        console.log('Deleted Successfully!', res);
        UserData();
        UserRoleData();
      })
      .catch((err) => {
        console.error('Error creating rent:', err);
      });
}
</script>



<template>
  <div class="bg-body-tertiary">
    <div class="container">
      <div class="row py-4 px-3">
        <div class="col-12">
          <div class="mb-4">
            <RouterLink class="small" :to="{ name: 'home' }">Back to Home</RouterLink>
            <h1 class="fs-3">Properties</h1>
          </div>
          <div>
            <table class="table">
              <thead>
              <tr>
                <!-- <th>Course ID</th> -->
                <th>User</th>
                <th>Action</th>
              </tr>
              </thead>
              <tbody v-if="data">
              <tr v-for="user in data">
                <td>{{ 'User Id: ' + user.id + ", " + 'Username: ' + user.username + ", " + 'First Name: ' + user.firstName + ", " + 'Last Name: ' + user.lastName + ", " + 'Email: ' + user.email + ", " + 'Phone Number: ' + user.phoneNumber + ", " + 'AFM: ' + user.afm + ", " + 'Id Number: ' + user.idnumber}}</td>
                <td v-if="userData?.roles?.includes('ROLE_ADMIN')">
                  <button @click="onSubmit(user.id)" type ="button" class="btn btn-info btn-sm">
                    Delete User
                  </button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

