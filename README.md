
[![Build Status](http://build.renjin.org/job/renjin-idea/badge/icon)](http://build.renjin.org/job/renjin-idea/)

# Renjin IDEA

The very beginnings of an Intellij Plugin for Renjin, mainly
intended to support Renjin development itself at this point. 

## Installation

Download the latest build of [renjin-idea.zip](http://build.renjin.org/job/renjin-idea/lastSuccessfulBuild/artifact/plugin/build/distributions/renjin-idea.zip) from build.renjin.org.


## Features

### Running Tests

Renjin uses a convention for tests that is similar to JUnit. For
example:

```.R
library(hamcrest)

test.sum <- function() {
    assertThat(sum(1:10), identicalTo(55L))
}

test.rep <- function() {
    assertThat(rep(NULL), identicalTo(NULL))
}
```

The plugin provides a Run Configuration called "Renjin Tests" that 
allow you to run and debug either a whole test file or a single test.

You should be able to right-click on the function body of `test.rep` for
example, and choose "Run test.rep()"

If you debug the tests, breakpoints in Renjin's Java source will be hit.


## Credits

The parser/lexer has been imported wholesale from the 
[r4intellij](https://github.com/holgerbrandl/r4intellij) project.



