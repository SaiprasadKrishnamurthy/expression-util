# Utility to convert Common Canonical Query to a native query specific to the data stores.

## Currently only MVEL expressions are supported (which is an infix notation of a query).
## Sample Input 1.
```
{
  "groupType": "AND",
  "predicates": [
    {
      "field": "hitId",
      "operator": "EqualTo",
      "value": "hitId"
    },
    {
      "field": "nationality",
      "operator": "EqualTo",
      "value": "GBR"
    },
    {
      "field": "nationality",
      "operator": "EqualTo",
      "value": "IND"
    },
    {
      "field": "nationality",
      "operator": "EqualTo",
      "value": "GBR"
    },
    {
      "field": "age",
      "operator": "LesserThanEqualTo",
      "value": 10
    },
    {
      "field": "location",
      "operator": "ArrayContains",
      "value": 10
    },
    {
      "field": "location",
      "operator": "ArrayContainsAll",
      "value": ["a", "b"]
    }

  ]
}
```

## Sample Output 1.
```
(hitId='hitId' AND nationality='GBR' AND nationality='IND' AND nationality='GBR' AND age<=10 AND location contains 10 AND location.containsAll(["a","b"]))
```

## Sample Input 2 (one level nesting).
```
{
  "groupType": "AND",
  "predicates": [
    {
      "field": "hitId",
      "operator": "EqualTo",
      "value": "hitId"
    },
    {
      "field": "age",
      "operator": "LesserThanEqualTo",
      "value": 10
    },
    {
      "field": "location",
      "operator": "ArrayContains",
      "value": 10
    }
  ],
  "nested": {
    "groupType": "OR",
    "predicates": [
      {
        "field": "nationality",
        "operator": "EqualTo",
        "value": "GBR"
      },
      {
        "field": "nationality",
        "operator": "EqualTo",
        "value": "IND"
      }
    ]
  }
}
```

## Sample Output 2 (one level nesting).
```
  (hitId='hitId' AND age<=10 AND location contains 10 OR (nationality='GBR' OR nationality='IND'))
```

## Sample Input 3 (two level nesting).
```
{
  "groupType": "AND",
  "predicates": [
    {
      "field": "hitId",
      "operator": "EqualTo",
      "value": "hitId"
    },
    {
      "field": "age",
      "operator": "LesserThanEqualTo",
      "value": 10
    },
    {
      "field": "location",
      "operator": "ArrayContains",
      "value": 10
    }
  ],
  "nested": {
    "groupType": "AND",
    "predicates": [
      {
        "field": "nationality",
        "operator": "EqualTo",
        "value": "GBR"
      },
      {
        "field": "nationality",
        "operator": "EqualTo",
        "value": "IND"
      }
    ]
  }
}
```

## Sample Output 3 (two level nesting).
```
(hitId='hitId' AND age<=10 AND location contains 10 OR (nationality='GBR' OR nationality='IND' AND (location='Sussex' AND location='Surrey')))
```

## Model Objects.
* Refer to file com.github.saiprasadkrishnamurthy.expression.model.Models.kt
* PredicateGroup is the type that represents a canonical query.


