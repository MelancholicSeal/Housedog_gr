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

if (!userData?.roles.includes('ROLE_ADMIN')) {
  router.push({ name: 'home' });
}

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
            <h1 class="fs-3">Users</h1>
          </div>
          <div>
            <table class="table">
              <thead>
                <tr>
                  <th>User Id</th>
                  <th>Username</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Email</th>
                  <th>Phone Number</th>
                  <th>AFM</th>
                  <th>Id Number</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody v-if="loading">
                <tr>
                  <td colspan="9">Loading...</td>
                </tr>
              </tbody>
              <tbody v-if="data">
              <tr v-for="user in data">
                <td>{{user.id}}</td>
                <td>{{user.username}}</td>
                <td>{{user.firstName}}</td>
                <td>{{user.lastName}}</td>
                <td>{{user.email}}</td>
                <td>{{user.phoneNumber}}</td>
                <td>{{user.afm}}</td>
                <td>{{user.idnumber}}</td>
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

