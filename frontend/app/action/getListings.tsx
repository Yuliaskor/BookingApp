import axios from 'axios';

export interface IListingsParams {
  userId?: string;
  guestCount?: number;
  roomCount?: number;
  bathroomCount?: number;
  startDate?: string;
  endDate?: string;
  locationValue?: string;
  category?: string;
}

interface ILocation {
  latitude: number;
  longitude: number;
  city: string;
  country: string;
  description: string;
}

interface IHost {
  id: number;
  email: string;
  name: string;
  aboutMe: string;
}

interface IListing {
  id: number;
  host: IHost;
  title: string;
  description: string;
  location: ILocation;
  photos: string;
  pricePerNight: number;
  maxGuests: number;
  availableFrom: string;
  availableTo: string;
  roomType: string;
  reservations: any[];
  beds: string[];
  amenities: string[];
  [key: string]: any;
}

export default async function getListings(params: IListingsParams) {
  try {

    const {
      userId,
      roomCount, 
      guestCount, 
      bathroomCount, 
      locationValue,
      startDate,
      endDate,
      category,
    } = params;

    
    // Replace with your API URL
    const response = await axios.get('http://localhost:8080/api/v1/listings');

    let listings = response.data.content;

    if (category) {
      listings = listings.filter((listing: IListing) => listing.category === category);
    }

    if (roomCount) {
      listings = listings.filter((listing: IListing)  => listing.numberOfRooms >= roomCount);
    }

    if (guestCount) {
      listings = listings.filter((listing: IListing)  => listing.maxGuests >= guestCount);
    }

    if (bathroomCount) {
      listings = listings.filter((listing: IListing)  => listing.numberOfBathrooms >= bathroomCount);
    }

    if (locationValue) {
      listings = listings.filter((listing: IListing)  => listing.location?.description === locationValue);
    }


    // Sort by createdAt in descending order
    const safeListings = listings.map((listing: IListing) => ({
      ...listing,
      createdAt: new Date(listing.availableFrom).toISOString(),
    }));

    console.log(safeListings);

    return safeListings;
  } catch (error: any) {
    throw new Error(error);
  }
}
