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

export default async function getListings() {
  try {
    // Replace with your API URL
    const response = await axios.get('http://localhost:8080/api/v1/listings');

    const listings = response.data.content;

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
