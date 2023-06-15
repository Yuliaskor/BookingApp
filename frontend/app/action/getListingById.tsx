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

interface IParams {
  listingId?: string;
}

export default async function getListingById(
  params: IParams
) {
  try {
    const { listingId } = params;
    // Replace with your API URL
    const response = await axios.get(`http://localhost:8080/api/v1/listings/${listingId}`);

    const listing = response.data;

    // Transform availableFrom into ISO string
    const safeListing = {
      ...listing,
      createdAt: new Date(listing.availableFrom).toISOString(),
    };

    console.log(safeListing);

    return safeListing;
  } catch (error: any) {
    throw new Error(error);
  }
}
