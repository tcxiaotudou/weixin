package cn.fciasth.weixin.pojo;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MyFriendsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMyUserIdIsNull() {
            addCriterion("my_user_id is null");
            return (Criteria) this;
        }

        public Criteria andMyUserIdIsNotNull() {
            addCriterion("my_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andMyUserIdEqualTo(String value) {
            addCriterion("my_user_id =", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdNotEqualTo(String value) {
            addCriterion("my_user_id <>", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdGreaterThan(String value) {
            addCriterion("my_user_id >", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("my_user_id >=", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdLessThan(String value) {
            addCriterion("my_user_id <", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdLessThanOrEqualTo(String value) {
            addCriterion("my_user_id <=", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdLike(String value) {
            addCriterion("my_user_id like", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdNotLike(String value) {
            addCriterion("my_user_id not like", value, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdIn(List<String> values) {
            addCriterion("my_user_id in", values, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdNotIn(List<String> values) {
            addCriterion("my_user_id not in", values, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdBetween(String value1, String value2) {
            addCriterion("my_user_id between", value1, value2, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyUserIdNotBetween(String value1, String value2) {
            addCriterion("my_user_id not between", value1, value2, "myUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdIsNull() {
            addCriterion("my_friend_user_id is null");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdIsNotNull() {
            addCriterion("my_friend_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdEqualTo(String value) {
            addCriterion("my_friend_user_id =", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdNotEqualTo(String value) {
            addCriterion("my_friend_user_id <>", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdGreaterThan(String value) {
            addCriterion("my_friend_user_id >", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("my_friend_user_id >=", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdLessThan(String value) {
            addCriterion("my_friend_user_id <", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdLessThanOrEqualTo(String value) {
            addCriterion("my_friend_user_id <=", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdLike(String value) {
            addCriterion("my_friend_user_id like", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdNotLike(String value) {
            addCriterion("my_friend_user_id not like", value, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdIn(List<String> values) {
            addCriterion("my_friend_user_id in", values, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdNotIn(List<String> values) {
            addCriterion("my_friend_user_id not in", values, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdBetween(String value1, String value2) {
            addCriterion("my_friend_user_id between", value1, value2, "myFriendUserId");
            return (Criteria) this;
        }

        public Criteria andMyFriendUserIdNotBetween(String value1, String value2) {
            addCriterion("my_friend_user_id not between", value1, value2, "myFriendUserId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}