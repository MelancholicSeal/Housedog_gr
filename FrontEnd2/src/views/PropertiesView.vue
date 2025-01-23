<script setup>
import { onMounted, ref } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

// @EXERCISE : Create dynamic pagination mechanism page=PAGE size=SIZE
// + create a generic reusable component.
const urlRef = ref(backendEnvVar + '/api/property?page=0&size=100');
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlRef, authRef);
const { cities, loading2, performRequest2 } = useRemoteData(urlRef, authRef);

onMounted(() => {
    performRequest();
});
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
                    <div class="checkbox-group">
                      <label v-for="category in categories" :key="category.id">
                        <input
                            type="checkbox"
                            :value="category.id"
                            v-model="selectedCategories"
                        />
                        {{ category.name }}
                      </label>
                    </div>
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

<script>
export default {
  name: 'App',
  data() {
    return {
      // Data for categories
      categories: [
        { id: 1, name: 'Electronics' },
        { id: 2, name: 'Furniture' },
        { id: 3, name: 'Clothing' }
      ],

      // Data for items to filter
      items: [
        { id: 1, name: 'Laptop', category: 'Electronics' },
        { id: 2, name: 'Sofa', category: 'Furniture' },
        { id: 3, name: 'T-shirt', category: 'Clothing' },
        { id: 4, name: 'Phone', category: 'Electronics' },
        { id: 5, name: 'Chair', category: 'Furniture' },
      ],

      // Selected categories for filtering
      selectedCategories: []
    };
  },
  computed: {
    // Computed property for filtered items
    filteredItems() {
      if (this.selectedCategories.length === 0) {
        return this.items;
      }
      return this.items.filter(item =>
          this.selectedCategories.includes(this.categories.find(c => c.name === item.category)?.id)
      );
    }
  }
};
</script>