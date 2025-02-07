<script setup>
import {onMounted, ref, watch} from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
import {useApplicationStore} from "@/stores/application.js";
const backendEnvVar = import.meta.env.VITE_BACKEND;
const { userData } = useApplicationStore();

const urlRef = ref(backendEnvVar + '/api/property?page=0&size=100');
const urlRefCity = ref(backendEnvVar + '/api/property/cities');
const urlRefType = ref(backendEnvVar + '/api/property/types');
const urlRole = ref(backendEnvVar + '/api/users/' + userData.id);
const authRef = ref(true);
const data = ref(null);
const {loading, performRequest: PropertyData } = useRemoteData(urlRef, authRef,data);
const cities = ref(null);
const {loading2, performRequest: CitiesData } = useRemoteData(urlRefCity, authRef,cities);
const propertytypes = ref(null);
const {loading3, performRequest: PropertyTypeData } = useRemoteData(urlRefType, authRef, propertytypes);
const role_data = ref(null);
const {loading4, performRequest: UserRoleData } = useRemoteData(urlRole, authRef, role_data);

const selectedCities = ref([]);
const selectedTypes = ref([]);
const selectedAvailability = ref(false);

const showFilters = ref(false);
onMounted(() => {
  CitiesData();
  PropertyData();
  PropertyTypeData();
  UserRoleData();
  console.log(data);
});

const applicationStore = useApplicationStore();
const userRoles = applicationStore.userData.roles;
console.log(userRoles);

const onSubmit = (id) => {
  const urlRefDelete = ref(backendEnvVar + + '/api/property' + id);
  const methodRef = ref('DELETE');
  const {loading5, performRequest: DeleteProperty } = useRemoteData(urlRefDelete, authRef, methodRef, data);
  DeleteProperty()
      .then((res) => {
        console.log('Deleted Successfully!', res);
      })
      .catch((err) => {
        console.error('Error creating rent:', err);
      });
}

const applyFilters = () => {
  console.log("Applied Filters:");
  console.log("Selected Cities:", selectedCities.value);
  console.log("Selected Types:", selectedTypes.value);
  console.log("Availability:", selectedAvailability.value);
  const filterParams = [];

  if (selectedCities.value.length) {
    filterParams.push(`cities=${selectedCities.value.join(',')}`);
  }
  else{
    filterParams.push(`cities=${cities._rawValue.join(',')}`);
  }
  if (selectedTypes.value.length) {
    console.log(cities);
    filterParams.push(`types=${selectedTypes.value.join(',')}`);
  }
  else{
    filterParams.push(`types=${propertytypes._rawValue.join(',')}`);
  }
  if (selectedAvailability.value !== null) {
    filterParams.push(`available=${selectedAvailability.value}`);
  }

  // Construct the URL with query parameters
  const filtersUrl = `?${filterParams.join('&')}`;
  const urlRef = ref(backendEnvVar + '/api/property/filter' + filtersUrl);
  console.log(filtersUrl);
  const {loading, performRequest: PropertyData } = useRemoteData(urlRef, authRef,data);
  PropertyData();

};
</script>



<template>
    <div class="bg-body-tertiary">
        <div class="container">
            <div class="row py-4 px-3">
                <div class="col-12">
                    <div class="mb-4">
                        <RouterLink class="small" :to="{ name: 'home' }">Back to Home</RouterLink>
                        <h1 class="fs-3">Properties</h1>
                        <RouterLink class="small" :to="{ name: 'property-new' }" v-if="userRoles === 'ROLE_OWNER'"
                            >Create Property</RouterLink>
                    </div>
                  <button @click="showFilters = !showFilters" class="btn btn-primary mb-3">
                    {{ showFilters ? 'Hide Filters' : 'Show Filters' }}
                  </button>



                  <div v-show="showFilters" class="checkbox-group-cities">
                    <h6 style="font-weight: bold; text-decoration: underline; margin-bottom: 3px;">Cities</h6>
                    <label v-for="(city, index) in cities" :key="index" style="margin-right: 10px">
                      <input
                          type="checkbox"
                          :value="city"
                          v-model="selectedCities"
                      />
                      {{ city }}
                    </label>
                  </div>


                  <div v-show="showFilters" class="checkbox-group-av">
                    <h6 style="font-weight: bold; text-decoration: underline; margin-bottom: 3px;">Availability</h6>
                      <input
                          type="checkbox"
                          :value="true"
                          v-model="selectedAvailability"
                      />
                      Only Available
                  </div>


                  <div v-show="showFilters" class="checkbox-group-type">
                    <h6 style="font-weight: bold; text-decoration: underline; margin-bottom: 3px;">Property Type</h6>
                    <label v-for="(type, index) in propertytypes" :key="index" style="margin-right: 10px">
                      <input
                          type="checkbox"
                          :value="type"
                          v-model="selectedTypes"
                      />
                      {{ type }}
                    </label>
                  </div>

                  <button v-show="showFilters" @click="applyFilters" class="btn btn-primary mt-3">
                    Apply Filters
                  </button>

                    <div>
                        <table class="table">
                            <thead>
                                <tr>
                                    <!-- <th>Course ID</th> -->
                                    <th>Property</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody v-if="data">
                                <tr v-for="property in data">
                                    <td>{{ property.type+","+property.city+","+property.address+","+property.owner.username}}</td>
                                    <td>
                                        <!-- TODO course.id -->
                                        <RouterLink
                                            :to="{
                                                name: 'property-details',
                                                params: { id: property.id }
                                            }"
                                        >
                                            Display
                                        </RouterLink>
                                    </td>
                                    <td v-if="data.role === 'ROLE_ADMIN'" v-for="property in data">
                                      <button @click="onSubmit(property.id)" type ="button" class="btn btn-info btn-sm">
                                        Delete Property
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

