---
name: Library Issue
about: Describe this issue template's purpose here.
title: ''
labels: ''
assignees: ''

---


Before filing a feature request:
-----------------------
- Search existing open issues: https://github.com/curioustools/curiousutils/issues
- Search existing pull requests: https://github.com/curioustools/curiousutils/pulls

When filing a feature request:
-----------------------
Replace the content in the sections below.


## Library Details
- Version:  
- package : eg `core-jdk`

[provide general introduction to the issue logging and why it is relevant to this repository]

## Context

[provide more detailed introduction to the issue itself and why it is relevant]

## Process

[ordered list the process to finding and recreating the issue, example below]

1. User goes to delete a dataset (to save space or whatever)
2. User gets popup modal warning
3. User deletes and it's lost forever

## Expected result

[describe what you would expect to have resulted from this process]

## Current result

[describe what you you currently experience from this process, and thereby explain the bug]

## Possible Fix

[not obligatory, but suggest fixes or reasons for the bug]

* Modal tells the user what dataset is being deleted, like “You are about to delete this dataset: car_crashes_2014.”
* A temporary "Trashcan" where you can recover a just deleted dataset if you mess up (maybe it's only good for a few hours, and then it cleans the cache assuming you made the right decision).

## `name of issue` screenshot

[if relevant, include a screenshot]

----

## Examples

* [Mulitpolygon issue](https://github.com/CartoDB/cartodb-platform/issues/721)
* [Madrid Projection issue](https://github.com/CartoDB/cartodb-platform/issues/579)
* [SQL Layer deletion issue](https://github.com/CartoDB/cartodb-platform/issues/598)
* [Table Deletion Issue](https://github.com/CartoDB/cartodb-platform/issues/722)
