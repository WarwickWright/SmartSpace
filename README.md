# SmartSpace
Code Challenge For Job Interview

Didn't get this all done as it was quite a big project.
Would have liked to added Room to persist the data and branched the repo units to net or local.
I've added some open ends to save the images locally with the Base64 code.
There's also some unused code in case you want to download the images and convert them straight to Base64 to display and save them  to room in the onlineData class.
Would have also used dagger to persist the data objects in the App class to make it easier to deal with screen rotation. I've added the App class as an open end in case you want a splash screen.
Displaying all the images on the details fragment would have been a bit of a big overhead as I would have had to create dynamic view arrays for the icon images
I've done a basic details page but not complete.

The Way I would have completed it is to use the HttpUrlConnection class instead of Retrofit.
This is because the GSon aspect of Retrofit causes noticalbe UI delays because of the expensive reflection, though I'd probably use it without GSon.
My way would cause a slightly bigger developer overhead as I had to make the data objects and deserialisaion classes manually as I have done, but I think it' worth the improved UI responsiveness. Also the GSon RxJava combination means having to parse data to frags via liveData because GSon doesn't create Parcelable objects. When viewing other developers code this means that I have seen them Use RxJava and liveData which I think is redundant as this means using the Rx observables to trigger liveData observables. I figure it's better just to use liveData without Rx.

If you want to give me more time, I'll add these things and re-push later.
