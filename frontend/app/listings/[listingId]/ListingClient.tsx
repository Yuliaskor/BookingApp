'use client';

import axios from "axios";
import { useCallback, useEffect, useMemo, useState } from "react";
import { toast } from "react-hot-toast";
import { Range } from "react-date-range";
import { useRouter } from "next/navigation";
import { differenceInDays, eachDayOfInterval } from 'date-fns';
import { DiscussionEmbed } from 'disqus-react';

import Container from "@/app/components/Container";
import { categories } from "@/app/components/navbar/Categories";
import ListingHead from "@/app/components/listings/ListingHead";
import ListingInfo from "@/app/components/listings/ListingInfo";
import ListingReservation from "@/app/components/listings/ListingReservation";
import useReserveModal from "@/app/hooks/useReserveModal";

const initialDateRange = {
  startDate: new Date(),
  endDate: new Date(),
  key: 'selection'
};

interface CurrentUser {
    name?: string | null;
    email?: string | null;
    image?: string | null;
    createdAt?: string | null;
    updatedAt?: string | null;
    emailVerified?: string | null;
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
    photos: string[];
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

interface ListingClientProps {
//  reservations?: SafeReservation[];
  listing: IListing & {
    user: CurrentUser;
  };
 // currentUser?: SafeUser | null;
}

const ListingClient: React.FC<ListingClientProps> = ({
  listing,
 // reservations = [],
//  currentUser
}) => {
  const disqusShortname = "airbnb-web";
  const disqusConfig = {
    url: `http://localhost:3000/listings/${listing.id}`,
    identifier: `listing_${listing.id}`,
    title: `listing_${listing.id}`
  };

  const reserveModal = useReserveModal();
  const router = useRouter();
  console.log(listing);
 

//   const disabledDates = useMemo(() => {
//     let dates: Date[] = [];

//     reservations.forEach((reservation: any) => {
//       const range = eachDayOfInterval({
//         start: new Date(reservation.startDate),
//         end: new Date(reservation.endDate)
//       });

//       dates = [...dates, ...range];
//     });

//     return dates;
//   }, [reservations]);

  const category = useMemo(() => {
     return categories.find((items) => 
      items.label === listing.category);
  }, [listing.category]);

  const [isLoading, setIsLoading] = useState(false);
  const [totalPrice, setTotalPrice] = useState(listing.pricePerNight);
  const [dateRange, setDateRange] = useState<Range>(initialDateRange);

  const onCreateReservation = useCallback(() => {

      setIsLoading(true);

      axios.post('/api/reservations', {
        totalPrice,
        startDate: dateRange.startDate,
        endDate: dateRange.endDate,
        listingId: listing?.id
      })
      .then(() => {
        toast.success('Listing reserved!');
        setDateRange(initialDateRange);
        router.push('/trips');
      })
      .catch(() => {
        toast.error('Something went wrong.');
      })
      .finally(() => {
        setIsLoading(false);
      })
  },
  [
    totalPrice, 
    dateRange, 
    listing?.id,
    router,
   // currentUser,
    reserveModal
  ]);

  useEffect(() => {
    if (dateRange.startDate && dateRange.endDate) {
      const dayCount = differenceInDays(
        dateRange.endDate, 
        dateRange.startDate
      );
      
      if (dayCount && listing.pricePerNight) {
        setTotalPrice(dayCount * listing.pricePerNight);
      } else {
        setTotalPrice(listing.pricePerNight);
      }
    }
  }, [dateRange, listing.pricePerNight]);

  return ( 
    <Container>
      <div 
        className="
          max-w-screen-lg 
          mx-auto
        "
      >
        <div className="flex flex-col gap-6">
          <ListingHead
            title={listing.title}
            imageSrc={listing.photos[0]}
            locationValue = {listing.location?.description}
            id={listing.id.toString()}
         //   currentUser={currentUser}
          />
          <div 
            className="
              grid 
              grid-cols-1 
              md:grid-cols-7 
              md:gap-10 
              mt-6
              mb-6
            "
          >
            <ListingInfo
              user={listing.user}
              category={category}
              description={listing.description}
              roomCount={listing.numberOfRooms}
              guestCount={listing.maxGuests}
              bathroomCount={listing.numberOfBathrooms}
              locationValue={listing.locationValue}
            />
            <div 
              className="
                order-first 
                mb-10 
                md:order-last 
                md:col-span-3
              "
            >
              <ListingReservation
                price={listing.pricePerNight}
                totalPrice={totalPrice}
                onChangeDate={(value) => setDateRange(value)}
                dateRange={dateRange}
                onSubmit={reserveModal.onOpen}
                disabled={isLoading}
               // disabledDates={disabledDates}
              />
            </div>
          </div>
          <hr></hr>
          <DiscussionEmbed
            shortname={disqusShortname}
            config={disqusConfig}
          />
        </div>
      </div>
    </Container>
   );
}
 
export default ListingClient;