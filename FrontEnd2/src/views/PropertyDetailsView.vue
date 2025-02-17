<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
import { useApplicationStore } from '@/stores/application.js';

const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const route = useRoute();
const applicationStore = useApplicationStore();
const userId = applicationStore.userData?.id;


const propertyIdRef = ref(null);
const urlRef = computed(() => {
    return backendEnvVar + '/api/property/' + propertyIdRef.value;
});
const urlRent = ref(backendEnvVar + '/api/rent/new');
const authRef = ref(true);
const data = ref(null);
const rent_data = ref(null);
const methodRef = ref('POST');
const {loading, performRequest: PropertyData } = useRemoteData(urlRef, authRef,data);
const message = ref('');
const showMessage = ref(false)

const onSubmit = () => {
  const formDataRef = ref({
    rented: false,
    user: {
      id: userId
    },
    property: {
      id: propertyIdRef.value,
    }
  });
  const {loading2, performRequest: RentData } = useRemoteData(urlRent, authRef,rent_data, methodRef, formDataRef);
  RentData()
      .then((response) => {
        console.log('Rent created successfully:', response);
        message.value = 'Rent request submitted successfully!';
        showMessage.value = true;
      })
      .catch((err) => {
        console.error('Error creating rent:', err);
        message.value = 'An error occurred while submitting rent:';
        showMessage.value = true;
      });
}

onMounted(() => {
    propertyIdRef.value = route.params.id;
    PropertyData();
});
</script>
<template>
    <div>
        <table class="table">
            <tbody v-if="data">
                <tr>
                    <th>ID</th>
                    <td>{{ data.id }}</td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td>{{ data.address }}</td>
                </tr>
                <tr>
                    <th>City</th>
                    <td>{{ data.city }}</td>
                </tr>
                <tr>
                  <th>Type</th>
                  <td>{{ data.type }}</td>
                </tr>
                <tr>
                  <th>Available</th>
                  <td>{{ data.available }}</td>
                </tr>
                <tr v-if="data.available === true && !applicationStore.userData?.roles?.includes('ROLE_ADMIN')">
                  <th></th>
                  <td>
                    <button @click="onSubmit" type ="button" class="btn btn-info btn-sm">
                      Rent Property
                    </button>
                  </td>
                </tr>
            </tbody>
        </table>
      <div v-if="showMessage" class="alert alert-info mt-3">
        {{ message }}
      </div>
    </div>
</template>

<style scoped>
.alert {
  padding: 10px;
  border-radius: 5px;
  background-color: #d1ecf1;
  color: #0c5460;
  border: 1px solid #bee5eb;
}
</style>