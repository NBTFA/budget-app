<template>
  <div class="mention-item">
    <i class="fa-solid fa-circle-exclamation"></i>
    <div class="mention-content">
      <div class="mention-title">{{ mention.from_user_name }}</div>
      <div class="mention-details">{{ mention.message }}</div>
    </div>
    <div class="mention-time">{{ daysAgo(mention.created_at) }} 天前</div>
  </div>
</template>
  
  <script>
export default {
  name: "MentionItem",
  props: {
    mention: {
      type: Object,
      required: true,
    },
  },
  methods: {
    daysAgo(dateString) {
        if (!dateString) {
      console.error('dateString is undefined or empty');
      return 'Unknown date';
    }
      const isoString = dateString.replace(" ", "T") + "Z";
      const createdTime = new Date(isoString);
      const now = new Date();
      const differenceInTime = now.getTime() - createdTime.getTime();
      return Math.floor(differenceInTime / (1000 * 3600 * 24));
    },
  },
};
</script>
  
  <style>
.mention-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.mention-content {
  margin-left: 10px;
}

.mention-title {
  font-weight: bold;
}

.mention-details {
  margin-top: 4px;
}

.mention-time {
  margin-left: auto;
  color: grey;
}
</style>
  