# Pre-work - *ToDo*

**ToDo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Payal Menon**

Time spent: **4** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] User can delete a ToDo from the list and it also deletes from database
* [X] Implemented dependency injection using Dragger

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/PayalMenon/ToDo/blob/master/ToDo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** [Android has rice UI elements as compared to other platforms. The usage of Layouts help organize the UI and animation framework has made the display of basic animations very easy. Fragments have helped develop mini UI and functional elements which can only be used based on requirement and reused, but also helps retain states across configuration change giving the user a seemless experience].

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** [I have used recycler list view and recycler adapter which is a enhanced version of list. The recycler view implements the view holder patter which makes sure that the new views are created only and only if the views are null, other view the existing views are reused. The findViewById expensive call is thus not made every time which improves the performance. Also the list items are only updates as and when needed and only the ones visible are updated which helps in performance improvement.].

## Notes

Describe any challenges encountered while building the app.

## License

    Copyright [2017] [Payal Menon]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
