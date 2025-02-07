<script>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
import {useApplicationStore} from "@/stores/application.js";
const backendEnvVar = import.meta.env.VITE_BACKEND;

// api/users/id     api/property/id

const route = useRoute();
const { userData } = useApplicationStore();
const ownerId = userData.id;

const userIdRef = ref(null);
const urlRef = computed(() => {
  return backendEnvVar+'/api/rent/requests/' + ownerId;
});

const authRef = ref(true);
const data = ref(null);
const { loading, performRequest } = useRemoteData(urlRef, authRef, data);

onMounted(() => {
  performRequest();
  console.log(data);
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
        <th>Renter Name</th>
        <th>Renter Email</th>
        <th>Renter Phone Number</th>
      </tr>
      </thead>
      <tbody v-if="loading">
      <tr>
        <td colspan="6">Loading...</td>
      </tr>
      </tbody>
      <tbody v-if="data">
      <tr v-for="rent in data">

        <td>{{ rent.property.address}}</td>
        <td>{{ rent.property.city}}</td>
        <td>{{ rent.property.type}}</td>
        <td>{{ rent.user.username}}</td>
        <td>{{ rent.user.email}}</td>
        <td>{{ rent.user.phoneNumber}}</td>
      </tr>
      </tbody>
    </table>
  </div>

</template>