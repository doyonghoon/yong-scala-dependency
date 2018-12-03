# Warmup problem

Implement the following in Scala

```
f(x) = f(x - 1) + f(x - 1)
f(0) = 1
```

### Solution 1

This takes `O(2^n)` for the time complexity with a constant space. As following
the definition, for each recursive function call, it will call twice recursive
function calls so that the number of function calls will be exactly 2 * (2^n) - 1
in total. We do -1 because the original function call is called only once.

```scala
def calculate(n: Int): Int = {
  if (n == 0) {
    return 1
  }

  return calculate(n - 1) + calculate(n - 1)
}
```

### Solution 1.5

A slightly better approach to improve the solution 1 is that we may return as
the following so that we can reduce the number of duplicated calculations

```scala
return 2 * calculate(n - 1)
```

However, this still takes an exponential time to complete the calculation so
that it is not an asymptotic improvement of the running time complexity.

### Solution 2

The following solution would take `O(ln n)` for the running time complexity with
a constant space. First notice that for each function call, because it calls
two other sub-function calls so that we can expect the number of function calls
would be an exponential at the end. Now, to notice the pattern better, observe
the following unravelling:

```
f(x) = 2 * f(x - 1)
     = 2 * (2 * f(x - 2))
     = 2 * 2 * ( 2 * f(x - 3))
     = 2 * 2 * 2 * (2 * f(x - 4))
     ...
     = 2^(k - 1) * f(x - k - 1)
     = 2^(k) * f(x - k)
     = 2^(k) * (1) (when x = k by def. that f(0) = 1)
```

Thus, now we know that `f(x) = 2^x`. The problem then reduces to solve the
power x of 2. My high level strategy solving the problem is that it is similar
to using two pointers in a number line going towards to the number multiple of 2
in `ln(n)` iterations. The following is a complete working code in Scala.

```scala
def calculate(n: Int): Int = {
  var p = n
  var res = 1
  var x = 2
  while (p > 0) {
    if (p % 2 == 1) {
      res *= x
    }

    p /= 2
    x *= x
  }

  res
}
```
