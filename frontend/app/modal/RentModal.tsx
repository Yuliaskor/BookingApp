'use client';
import Modal from "./Modal";
import useRentModal from '@/app/hooks/useRentModal';
import { 
    FieldValues, 
    SubmitHandler, 
    useForm
  } from 'react-hook-form';
import { useMemo, useState } from "react";
import Heading from "../Heading";

import { categories } from "../components/navbar/Categories";
import CategoryInput from "../inputs/CategoryInput";
import CountrySelect from "../inputs/CountrySelect";
import dynamic from "next/dynamic";
import Counter from "../inputs/Counter";
import ImageUpload from "../inputs/ImageUpload";
import Input from "../inputs/Input";
import { useRouter } from 'next/navigation';

import axios from 'axios';
import { toast } from 'react-hot-toast';

import { FaWifi, FaTv, FaUtensils, FaSwimmingPool, FaParking, FaDumbbell } from 'react-icons/fa';
import AmenityBox from "../inputs/AmenityBox";
import {getSession} from "next-auth/react";

const amenities = [
    { label: 'Wifi', icon: FaWifi },
    { label: 'TV', icon: FaTv },
    { label: 'Kitchen', icon: FaUtensils },
    { label: 'Pool', icon: FaSwimmingPool },
    { label: 'Parking', icon: FaParking },
    { label: 'Gym', icon: FaDumbbell },
];

enum STEPS {
    CATEGORY = 0,
    LOCATION = 1,
    INFO = 2,
    IMAGES = 3,
    DESCRIPTION = 4,
    PRICE = 5,
    AMENITIES = 6,
  }

const RentModel = () => {
    const router = useRouter();
    const rentModal = useRentModal();
    const [step, setStep] = useState(STEPS.CATEGORY);
    const [isLoading, setIsLoading] = useState(false);
    const [selectedAmenities, setSelectedAmenities] = useState<string[]>([]);



    const { 
        register, 
        handleSubmit,
        setValue,
        watch,
        formState: {
          errors,
        },
        reset,
      } = useForm<FieldValues>({
        defaultValues: {
          category: '',
          location: null,
          guestCount: 1,
          roomCount: 1,
          bathroomCount: 1,
          imageSrc: '',
          price: 1,
          title: '',
          description: '',
          amenities: [],
        }
      });
    

     const category = watch('category');
     const location = watch('location');
     const guestCount = watch('guestCount');
     const roomCount = watch('roomCount');
     const bathroomCount = watch('bathroomCount');
     const imageSrc = watch('imageSrc');

     const setCustomValue = (id: string, value: any) => {
        setValue(id, value, {
          shouldDirty: true,
          shouldTouch: true,
          shouldValidate: true
        })
      }

      const Map = useMemo(() => dynamic(() => import('../components/Map'), { 
        ssr: false 
      }), [location]);

    const onBack = () => {
        setStep((value) => value - 1);
      }
    
      const onNext = () => {
        setStep((value) => value + 1);
      }

  const onSubmit: SubmitHandler<FieldValues> = async (data) => {
        if (step !== STEPS.AMENITIES) {
          return onNext();
        }
        // console.log(data);
        // console.log(category);
        // console.log()
        setIsLoading(true);

        const hostId = 1;

        const photoList = [];
        photoList.push(data.imageSrc);

        const upperCaseCategory = data.category.toUpperCase();
      
        // Adjust the data object to match the structure the API is expecting
        const postData = {
          title: data.title,
          description: data.description,
          location: {
            latitude: data.location.latlng[0],
            longitude: data.location.latlng[1],
            city: data.location.label,
            country: data.location.region,
            description: data.location.value,
          },
          photos: photoList,
          pricePerNight: parseInt(data.price),
          maxGuests: data.guestCount,
          availableFrom: "2021-01-01",
          availableTo: "2023-01-01",
          roomType: "ENTIRE_PLACE",
          numberOfRooms: data.roomCount,
          numberOfBathrooms: 1,
          amenities: selectedAmenities,
          category: upperCaseCategory,

        };

        console.log(postData);

        const session = await getSession();
        const token = session.id_token;
      
        axios.post(`http://localhost:8080/api/v1/hosts/${hostId}/listings`, postData, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          }
        })
          .then(() => {
            toast.success('Listing created!');
      
            router.refresh();
            reset();
            setStep(STEPS.CATEGORY);
            rentModal.onClose();
          })
          .catch(() => {
            toast.error('Something went wrong.');
          })
          .finally(() => {
            setIsLoading(false);
          });
      }      

      const actionLabel = useMemo(() => {
        if (step === STEPS.AMENITIES) {
          return 'Create'
        }
    
        return 'Next'
      }, [step]);

      const secondaryActionLabel = useMemo(() => {
        if (step === STEPS.CATEGORY) {
          return undefined
        }
    
        return 'Back'
      }, [step]);

      let bodyContent = (
        <div className="flex flex-col gap-8">
          <Heading
            title="Which of these best describes your place?"
            subtitle="Pick a category"
          />
          <div 
            className="
              grid 
              grid-cols-1 
              md:grid-cols-2 
              gap-3
              max-h-[50vh]
              overflow-y-auto
            "
          >
            {categories.map((item) => (
              <div key={item.label} className="col-span-1">
                <CategoryInput
                  onClick={(category) => 
                    setCustomValue('category', category)}
                 selected={category === item.label}
                  label={item.label}
                  icon={item.icon}
                />
              </div>
            ))}
          </div>
        </div>
      )

      if (step === STEPS.LOCATION) {
        bodyContent = (
          <div className="flex flex-col gap-8">
            <Heading
              title="Where is your place located?"
              subtitle="Help guests find you!"
            />
            <CountrySelect 
              value={location} 
              onChange={(value) => setCustomValue('location', value)} 
            />
            <Map center={location?.latlng} />
          </div>
        );
      }

      if (step === STEPS.INFO) {
        bodyContent = (
          <div className="flex flex-col gap-8">
            <Heading
              title="Share some basics about your place"
              subtitle="What amenitis do you have?"
            />
            <Counter 
              onChange={(value) => setCustomValue('guestCount', value)}
              value={guestCount}
              title="Guests" 
              subtitle="How many guests do you allow?"
            />
            <hr />
            <Counter 
              onChange={(value) => setCustomValue('roomCount', value)}
              value={roomCount}
              title="Rooms" 
              subtitle="How many rooms do you have?"
            />
            <hr />
            <Counter 
              onChange={(value) => setCustomValue('bathroomCount', value)}
              value={bathroomCount}
              title="Bathrooms" 
              subtitle="How many bathrooms do you have?"
            />
          </div>
        )
      }

      if (step === STEPS.IMAGES) {
        bodyContent = (
          <div className="flex flex-col gap-8">
            <Heading
              title="Add a photo of your place"
              subtitle="Show guests what your place looks like!"
            />
            <ImageUpload
              onChange={(value) => setCustomValue('imageSrc', value)}
              value={imageSrc}
            />
          </div>
        )
      }

      if (step === STEPS.DESCRIPTION) {
        bodyContent = (
          <div className="flex flex-col gap-8">
            <Heading
              title="How would you describe your place?"
              subtitle="Short and sweet works best!"
            />
            <Input
              id="title"
              label="Title"
              disabled={isLoading}
              register={register}
              errors={errors}
              required
            />
            <hr />
            <Input
              id="description"
              label="Description"
              disabled={isLoading}
              register={register}
              errors={errors}
              required
            />
          </div>
        )
      }

      if (step === STEPS.PRICE) {
        bodyContent = (
          <div className="flex flex-col gap-8">
            <Heading
              title="Now, set your price"
              subtitle="How much do you charge per night?"
            />
            <Input
              id="price"
              label="Price"
              formatPrice 
              type="number" 
              disabled={isLoading}
              register={register}
              errors={errors}
              required
            />
          </div>
        )
      }

      if (step === STEPS.AMENITIES) {
        bodyContent = (
            <div className="flex flex-col gap-8">
                <Heading
                    title="What amenities do you offer?"
                    subtitle="Select all that apply"
                />
                <div 
                  className="
                    grid 
                    grid-cols-1 
                    md:grid-cols-2 
                    gap-3
                    max-h-[50vh]
                    overflow-y-auto
                  "
                >
                    {amenities.map((amenity, index) => (
                    // <div key={index}>
                      
                      <AmenityBox 
                          icon={amenity.icon}  // I'm assuming each amenity has an icon here
                          label={amenity.label} 
                          selected={selectedAmenities.includes(amenity.label)} 
                          onClick={(amenityLabel) => {
                              if(selectedAmenities.includes(amenityLabel)){
                                  setSelectedAmenities(selectedAmenities.filter(a => a !== amenityLabel));
                              } else {
                                  setSelectedAmenities([...selectedAmenities, amenityLabel]);
                              }
                          }} 
                      />
                    // </div>
                ))}
                </div>
            
            </div>
        )
    }
    

    return (

        <Modal
       // disabled={isLoading}
        isOpen={rentModal.isOpen}
        title="Rent room!"
        actionLabel={actionLabel}
        onSubmit={handleSubmit(onSubmit)}
        secondaryActionLabel={secondaryActionLabel}
        secondaryAction={step === STEPS.CATEGORY ? undefined : onBack}
        onClose={rentModal.onClose}
        body={bodyContent}
      />
    );
}

export default RentModel;