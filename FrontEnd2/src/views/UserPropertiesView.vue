<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
import {useApplicationStore} from "@/stores/application.js";
const backendEnvVar = import.meta.env.VITE_BACKEND;
const { userData } = useApplicationStore();

const router = useRouter();
const route = useRoute();

const userIdRef = ref(null);
const urlRef = computed(() => {
    return backendEnvVar+'/api/owner/' + userIdRef.value + '/properties';
});

const authRef = ref(true);
const data = ref(null);
const { loading, performRequest } = useRemoteData(urlRef, authRef, data);

if (!userData?.roles.includes('ROLE_OWNER') || userData?.id.toString()!==route.params.id) {
  router.push({ name: 'home' });
}

onMounted(() => {
    userIdRef.value = route.params.id;
    performRequest();
    console.log(data);
});

const updatePropertyAvailability = (propertyId, isChecked) => {
  const updateUrlRef = ref(`${backendEnvVar}/api/property/${propertyId}/changeAvailability`);
  const updateMethodRef = ref('PUT');
  const updateBodyRef = ref({propertyId});
  const {performRequest: performUpdate} = useRemoteData(updateUrlRef, authRef, ref(null), updateMethodRef, updateBodyRef);
  performUpdate();
  if (isChecked){
    const urlRefDelete = ref(backendEnvVar + '/api/rent/delete/' + propertyId);
    console.log(urlRefDelete);
    const methodRefDelete = ref('PUT');
    const {loading2, performRequest: DeleteRent } = useRemoteData(urlRefDelete, authRef, data, methodRefDelete);
    DeleteRent();
  }
};

const addNewProperty = () => {
  router.push({ name: 'property-new' });
};
</script>

<template>
  <div class="bg-body-tertiary">
    <div class="container">
      <div class="row py-4 px-3">
        <div class="col-12">
    <div>
        <button @click="addNewProperty">Add Property</button>
        <table class="table">
            <thead>
                <tr>
                    <th>Property Address</th>
                    <th>Property City</th>
                    <th>Property Type</th>
                    <th>Property Availability</th>
                </tr>
            </thead>
            <tbody v-if="loading">
                <tr>
                    <td colspan="4">Loading...</td>
                </tr>
            </tbody>
            <tbody v-if="data">
                <tr v-for="property in data">
                    <td>{{ property.address }}</td>
                    <td>{{ property.city }}</td>
                    <td>{{ property.type }}</td>
                    <td>
                      <input
                          type="checkbox"
                          :checked="property.available"

                          @change="(e) => updatePropertyAvailability(property.id, e.target.checked)"
                      />
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
