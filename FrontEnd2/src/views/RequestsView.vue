<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
import {useApplicationStore} from "@/stores/application.js";
const backendEnvVar = import.meta.env.VITE_BACKEND;

const { userData } = useApplicationStore();


const urlRef =  ref(backendEnvVar +'/api/rent/requests/' + userData.id);
const authRef = ref(true);
const data = ref(null);
console.log(urlRef);
const { loading, performRequest: GetData } = useRemoteData(urlRef, authRef, data);

onMounted(() => {

  console.log(urlRef);
  GetData();

});
</script>

<template>

  <div>
    <table class="table">
      <thead>
      <tr>
        <th>Property Address</th>
        <th>Property City</th>
        <th>Property Type</th>
        <th>Renter First Name</th>
        <th>Renter Last Name</th>
        <th>Renter Email</th>
        <th>Renter Phone Number</th>
      </tr>
      </thead>
      <tbody v-if="loading">
      <tr>
        <td colspan="7">Loading...</td>
      </tr>
      </tbody>
      <tbody v-if="data">
      <tr v-for="item in data">
        <td>{{ item.property.address}}</td>
        <td>{{ item.property.city}}</td>
        <td>{{ item.property.type}}</td>
        <td>{{ item.user.firstName}}</td>
        <td>{{ item.user.lastName}}</td>
        <td>{{ item.user.email}}</td>
        <td>{{ item.user.phoneNumber}}</td>
      </tr>
      </tbody>
    </table>
  </div>

</template>