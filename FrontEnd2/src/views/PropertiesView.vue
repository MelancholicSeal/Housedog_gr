<script setup>
import {onMounted, ref, watch} from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

// @EXERCISE : Create dynamic pagination mechanism page=PAGE size=SIZE
// + create a generic reusable component.
const urlRef = ref(backendEnvVar + '/api/property?page=0&size=100');
const urlRefCity = ref(backendEnvVar + '/api/property/cities');
const urlRefType = ref(backendEnvVar + '/api/property/types');
const authRef = ref(true);
const data = ref(null);
const {loading, performRequest: PropertyData } = useRemoteData(urlRef, authRef,data);
const cities = ref(null);
const {loading2, performRequest: CitiesData } = useRemoteData(urlRefCity, authRef,cities);
const propertytypes = ref(null);
const {loading3, performRequest: PropertyTypeData } = useRemoteData(urlRefType, authRef, propertytypes);

const selectedCities = ref([]);
const selectedTypes = ref([]);
const selectedAvailability = ref(false);

const showFilters = ref(false);
onMounted(() => {
  CitiesData();
  PropertyData();
  PropertyTypeData();
  console.log(data);
});


const applyFilters = () => {
  console.log("Applied Filters:");
  console.log("Selected Cities:", selectedCities.value);
  console.log("Selected Types:", selectedTypes.value);
  console.log("Availability:", selectedAvailability.value);
  const filterParams = [];

  if (selectedCities.value.length) {
    filterParams.push(`cities=${selectedCities.value.join(',')}`);
  }
  if (selectedTypes.value.length) {
    filterParams.push(`types=${selectedTypes.value.join(',')}`);
  }
  if (selectedAvailability.value !== null) {
    filterParams.push(`available=${selectedAvailability.value}`);
  }

  // Construct the URL with query parameters
  const filtersUrl = `?${filterParams.join('&')}`;
  const urlRef = ref(backendEnvVar + '/api/property/filter' + filtersUrl);
  const {loading, performRequest: PropertyData } = useRemoteData(urlRef, authRef,data);
  PropertyData();
  console.log(filtersUrl);
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
                        <RouterLink class="small" :to="{ name: 'property-new' }"
                            >Create Property</RouterLink
                        >
                    </div>
                  <button @click="showFilters = !showFilters" class="btn btn-primary mb-3">
                    {{ showFilters ? 'Hide Filters' : 'Show Filters' }}
                  </button>

                  <div v-show="showFilters" class="checkbox-group-cities">
                    <label v-for="(city, index) in cities" :key="index">
                      <input
                          type="checkbox"
                          :value="city"
                          v-model="selectedCities"
                      />
                      {{ city }}
                    </label>
                  </div>

                  <div v-show="showFilters" class="checkbox-group-av">
                      <input
                          type="checkbox"
                          :value="true"
                          v-model="selectedAvailability"
                      />
                      Available
                  </div>

                  <div v-show="showFilters" class="checkbox-group-type">
                    <label v-for="(type, index) in propertytypes" :key="index">
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
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

