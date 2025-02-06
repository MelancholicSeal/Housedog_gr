<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const route = useRoute();

const userIdRef = ref(null);
const urlRef = computed(() => {
    return backendEnvVar+'/api/owner/' + userIdRef.value + '/properties';
});

const authRef = ref(true);
const data = ref(null);
const { loading, performRequest } = useRemoteData(urlRef, authRef, data);

onMounted(() => {
    userIdRef.value = route.params.id;
    performRequest();
    console.log(data);
});

const updatePropertyAvailability = (propertyId, updatedAvailability) => {
  const updateUrlRef = ref(`${backendEnvVar}/api/property/${propertyId}`);
  const updateMethodRef = ref('PUT');
  const updateBodyRef = ref({ available: updatedAvailability });

  const { performRequest: performUpdate } = useRemoteData(updateUrlRef, authRef, ref(null), updateMethodRef, updateBodyRef);

  performUpdate();
};

</script>

<template>
    <div>
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
</template>
